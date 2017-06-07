package com.wix.pay.paymentexpress.it


import com.google.api.client.http.javanet.NetHttpTransport
import com.wix.pay.creditcard.{CreditCard, CreditCardOptionalFields, YearMonth}
import com.wix.pay.model.{CurrencyAmount, Deal, Payment}
import com.wix.pay.paymentexpress.PaymentexpressMatchers._
import com.wix.pay.paymentexpress._
import com.wix.pay.paymentexpress.model.ErrorCodes
import com.wix.pay.paymentexpress.testkit.{PaymentexpressDriver, PaymentexpressTester}
import com.wix.pay.{PaymentErrorException, PaymentGateway, PaymentRejectedException}
import org.specs2.mutable.SpecWithJUnit
import org.specs2.specification.Scope


class PaymentexpressGatewayIT extends SpecWithJUnit with PaymentexpressTester {
  val paymentexpressPort = 10008

  val requestFactory = new NetHttpTransport().createRequestFactory()
  val driver = new PaymentexpressDriver(port = paymentexpressPort)
  step {
    driver.startProbe()
  }

  sequential

  trait Ctx extends Scope {
    val merchantParser = new JsonPaymentexpressMerchantParser()
    val authorizationParser = new JsonPaymentexpressAuthorizationParser()
    val helper = new PaymentexpressHelper

    val paymentexpress: PaymentGateway = new PaymentexpressGateway(
      requestFactory = requestFactory,
      endpointUrl = s"http://localhost:$paymentexpressPort/",
      merchantParser = merchantParser,
      authorizationParser = authorizationParser)

    driver.resetProbe()
  }

  "authorize request via PaymentExpress gateway" should {
    "gracefully fail on invalid merchant key" in new Ctx {
      val someMerchant = PaymentexpressMerchant(
        username = "someUsername",
        password = "somePassword"
      )
      val merchantKey = merchantParser.stringify(someMerchant)

      val someCurrencyAmount = CurrencyAmount("USD", 33.3)
      val somePayment = Payment(someCurrencyAmount, 1)
      val someCreditCard = CreditCard(
        number = "4012888818888",
        expiration = YearMonth(2020, 12),
        additionalFields = Some(CreditCardOptionalFields(
          csc = Some("123"))))

      val someDeal = Deal(
        id = "some deal ID",
        title = Some("some deal title"),
        description = Some("some deal description"))

      val errorMessage = "Authentication Error"
      val helpMessage = "Provider right credentials"
      driver.aRequestFor(helper.createAuthorizeRequest(
        merchant = someMerchant,
        currencyAmount = someCurrencyAmount,
        creditCard = someCreditCard,
        deal = Some(someDeal))
      ) returns anErrorResponse(
        code = ErrorCodes.authenticationError,
        message = errorMessage,
        help = helpMessage
      )

      paymentexpress.authorize(
        merchantKey = merchantKey,
        creditCard = someCreditCard,
        payment = somePayment,
        deal = Some(someDeal)
      ) must beAFailedTry.like {
        case e: PaymentErrorException => e.message must beEqualTo(s"${ErrorCodes.authenticationError} $errorMessage: $helpMessage")
      }
    }

    "successfully yield an authorization key on valid request" in new Ctx {
      val someMerchant = PaymentexpressMerchant(
        username = "someUsername",
        password = "somePassword"
      )
      val someMerchantKey = merchantParser.stringify(someMerchant)

      val someCurrencyAmount = CurrencyAmount("USD", 33.3)
      val somePayment = Payment(someCurrencyAmount, 1)
      val someCreditCard = CreditCard(
        number = "4012888818888",
        expiration = YearMonth(2020, 12),
        additionalFields = Some(CreditCardOptionalFields(
          csc = Some("123"))))

      val someDeal = Deal(
        id = "some deal ID",
        title = Some("some deal title"),
        description = Some("some deal description")
      )

      val someDpsTxnRef = "some dpsTxnRef"
      driver.aRequestFor(helper.createAuthorizeRequest(
        merchant = someMerchant,
        currencyAmount = someCurrencyAmount,
        creditCard = someCreditCard,
        deal = Some(someDeal))
      ) returns aSuccessfulResponse(
        authorized = true,
        dpsTxnRef = Some(someDpsTxnRef)
      )

      paymentexpress.authorize(
        merchantKey = someMerchantKey,
        creditCard = someCreditCard,
        payment = somePayment,
        deal = Some(someDeal)
      ) must beASuccessfulTry(
        check = beAuthorizationKey(
          authorization = beAuthorization(
            currency = ===(someCurrencyAmount.currency),
            dpsTxnRef = ===(someDpsTxnRef)
          )
        )
      )
    }

    "gracefully fail on rejected card" in new Ctx {
      val someMerchant = PaymentexpressMerchant(
        username = "someUsername",
        password = "somePassword"
      )
      val merchantKey = merchantParser.stringify(someMerchant)

      val someCurrencyAmount = CurrencyAmount("USD", 33.3)
      val somePayment = Payment(someCurrencyAmount, 1)
      val someCreditCard = CreditCard(
        number = "4012888818888",
        expiration = YearMonth(2020, 12),
        additionalFields = Some(CreditCardOptionalFields(
          csc = Some("123"))))

      val someDeal = Deal(
        id = "some deal ID",
        title = Some("some deal title"),
        description = Some("some deal description")
      )

      val someCardHolderResponseText = "some card holder response text"
      val someCardHolderResponseDescription = "some card holder response description"
      driver.aRequestFor(helper.createAuthorizeRequest(
        merchant = someMerchant,
        currencyAmount = someCurrencyAmount,
        creditCard = someCreditCard,
        deal = Some(someDeal))
      ) returns aSuccessfulResponse(
        authorized = false,
        cardHolderResponseText = Some(someCardHolderResponseText),
        cardHolderResponseDescription = Some(someCardHolderResponseDescription)
      )

      val expectedErrorMessage = s"$someCardHolderResponseText|$someCardHolderResponseDescription"
      paymentexpress.authorize(
        merchantKey = merchantKey,
        creditCard = someCreditCard,
        payment = somePayment,
        deal = Some(someDeal)
      ) must beAFailedTry.like {
        case e: PaymentRejectedException => e.message must beEqualTo(expectedErrorMessage)
      }
    }
  }

  "capture request via PaymentExpress gateway" should {
    "successfully yield a transaction ID on valid request" in new Ctx {
      val someMerchant = PaymentexpressMerchant(
        username = "someUsername",
        password = "somePassword"
      )
      val merchantKey = merchantParser.stringify(someMerchant)

      val someAuthorization = PaymentexpressAuthorization(
        currency = "some currency",
        dpsTxnRef = "some dpsTxnRef"
      )
      val authorizationKey = authorizationParser.stringify(someAuthorization)

      val someAmount = 11.1

      driver.aRequestFor(helper.createCaptureRequest(
        merchant = someMerchant,
        currencyAmount = CurrencyAmount(
          currency = someAuthorization.currency,
          amount = someAmount
        ),
        transactionId = someAuthorization.dpsTxnRef)
      ) returns aSuccessfulResponse(
        authorized = true,
        dpsTxnRef = Some(someAuthorization.dpsTxnRef)
      )

      paymentexpress.capture(
        merchantKey = merchantKey,
        authorizationKey = authorizationKey,
        amount = someAmount
      ) must beASuccessfulTry(
        check = ===(someAuthorization.dpsTxnRef)
      )
    }
  }

  "voidAuthorization request via PaymentExpress gateway" should {
    "successfully yield a transaction ID on valid request" in new Ctx {
      val someMerchant = PaymentexpressMerchant(
        username = "someUsername",
        password = "somePassword"
      )
      val merchantKey = merchantParser.stringify(someMerchant)

      val someAuthorization = PaymentexpressAuthorization(
        currency = "some currency",
        dpsTxnRef = "some dpsTxnRef"
      )
      val authorizationKey = authorizationParser.stringify(someAuthorization)

      paymentexpress.voidAuthorization(
        merchantKey = merchantKey,
        authorizationKey = authorizationKey
      ) must beASuccessfulTry(
        check = ===(someAuthorization.dpsTxnRef)
      )
    }
  }

  step {
    driver.stopProbe()
  }
}
