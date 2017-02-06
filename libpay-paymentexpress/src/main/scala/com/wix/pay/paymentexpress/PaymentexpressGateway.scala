package com.wix.pay.paymentexpress


import com.google.api.client.http._
import com.wix.pay.creditcard.CreditCard
import com.wix.pay.model.{CurrencyAmount, Customer, Deal, Payment}
import com.wix.pay.paymentexpress.model._
import com.wix.pay.{PaymentErrorException, PaymentException, PaymentGateway, PaymentRejectedException}

import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}


object Endpoints {
  val production = "https://sec.paymentexpress.com/pxpost.aspx"
}

class PaymentexpressGateway(requestFactory: HttpRequestFactory,
                            connectTimeout: Option[Duration] = None,
                            readTimeout: Option[Duration] = None,
                            numberOfRetries: Int = 0,
                            endpointUrl: String = Endpoints.production,
                            merchantParser: PaymentexpressMerchantParser = new JsonPaymentexpressMerchantParser,
                            authorizationParser: PaymentexpressAuthorizationParser = new JsonPaymentexpressAuthorizationParser) extends PaymentGateway {

  private val helper = new PaymentexpressHelper
  private val transactionRequestParser = new TransactionRequestParser
  private val transactionResponseParser = new TransactionResponseParser

  override def authorize(merchantKey: String, creditCard: CreditCard, payment: Payment, customer: Option[Customer], deal: Option[Deal]): Try[String] = {
    Try {
      require(payment.installments == 1, "PaymentExpress does not support installments")

      val merchant = merchantParser.parse(merchantKey)

      val request = helper.createAuthorizeRequest(
        merchant = merchant,
        currencyAmount = payment.currencyAmount,
        creditCard = creditCard,
        deal = deal)
      val response = transact(request)

      authorizationParser.stringify(PaymentexpressAuthorization(
        currency = payment.currencyAmount.currency,
        dpsTxnRef = response.Transaction.DpsTxnRef
      ))
    } match {
      case Success(transactionId) => Success(transactionId)
      case Failure(e: PaymentException) => Failure(e)
      case Failure(e) => Failure(PaymentErrorException(e.getMessage, e))
    }
  }

  override def capture(merchantKey: String, authorizationKey: String, amount: Double): Try[String] = {
    Try {
      val merchant = merchantParser.parse(merchantKey)
      val authorization = authorizationParser.parse(authorizationKey)

      val request = helper.createCaptureRequest(
        merchant = merchant,
        currencyAmount = CurrencyAmount(authorization.currency, amount),
        transactionId = authorization.dpsTxnRef)
      val response = transact(request)

      response.Transaction.DpsTxnRef
    } match {
      case Success(transactionId) => Success(transactionId)
      case Failure(e: PaymentException) => Failure(e)
      case Failure(e) => Failure(PaymentErrorException(e.getMessage, e))
    }
  }

  override def sale(merchantKey: String, creditCard: CreditCard, payment: Payment, customer: Option[Customer], deal: Option[Deal]): Try[String] = {
    Try {
      require(payment.installments == 1, "PaymentExpress does not support installments")
      val merchant = merchantParser.parse(merchantKey)

      val request = helper.createSaleRequest(
        merchant = merchant,
        currencyAmount = payment.currencyAmount,
        creditCard = creditCard,
        deal = deal)
      val response = transact(request)

      response.Transaction.DpsTxnRef
    } match {
      case Success(transactionId) => Success(transactionId)
      case Failure(e: PaymentException) => Failure(e)
      case Failure(e) => Failure(PaymentErrorException(e.getMessage, e))
    }
  }

  override def voidAuthorization(merchantKey: String, authorizationKey: String): Try[String] = {
    Try {
      val merchant = merchantParser.parse(merchantKey)
      val authorization = authorizationParser.parse(authorizationKey)

      // PaymentExpress doesn't support voiding an authorization - you can either issue a full refund or just wait 7 days.
      // I don't know if the two are equivalent (after 7 days) from both the payer's and payee's perspectives, so for
      // now, we just wait it out.
      authorization.dpsTxnRef
    }
  }

  private def transact(request: TransactionRequest): TransactionResponse = {
    val httpRequest = requestFactory.buildPostRequest(
      new GenericUrl(endpointUrl),
      new ByteArrayContent("application/xml", transactionRequestParser.stringify(request).getBytes("UTF-8")))

    connectTimeout foreach (to => httpRequest.setConnectTimeout(to.toMillis.toInt))
    readTimeout foreach (to => httpRequest.setReadTimeout(to.toMillis.toInt))
    httpRequest.setNumberOfRetries(numberOfRetries)

    val httpResponse = httpRequest.execute()
    try {
      val response = transactionResponseParser.parse(httpResponse.parseAsString)
      verifyResponse(response)
      response
    } finally {
      httpResponse.ignore()
    }
  }

  private def verifyResponse(response: TransactionResponse) {
    if (response.Success != Booleans.`true`) {
      throw PaymentErrorException(response.ResponseText)
    }

    // In some rare cases, PaymentExpress may transmit a transaction request to a PaymentExpress Host, but a response
    // is not received within a timeout period or the link to the PaymentExpress Host or beyond failed while awaiting a
    // response. In either of these circumstances, the result of the transaction is indeterminate and its status must
    // be polled later. Until we know this is a real issue, we just treat this as an error.
    // @see https://www.paymentexpress.com/Technical_Resources/Legacy_Interfaces/PxXml#exceptionhandling
    if (response.Transaction.StatusRequired != Booleans.`false`) {
      throw PaymentErrorException("PaymentExpress transaction status is indeterminate")
    }

    if (response.Transaction.Authorized != Booleans.`true`) {
      throw PaymentRejectedException(s"${response.Transaction.CardHolderResponseText}|${response.Transaction.CardHolderResponseDescription}")
    }
  }
}
