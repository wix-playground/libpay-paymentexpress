package com.wix.pay.paymentexpress


import java.text.DecimalFormat

import com.wix.pay.creditcard.CreditCard
import com.wix.pay.model.{CurrencyAmount, Deal}
import com.wix.pay.paymentexpress.model._


class PaymentexpressHelper {
  def createAuthorizeRequest(merchant: PaymentexpressMerchant, currencyAmount: CurrencyAmount,
                             creditCard: CreditCard, deal: Option[Deal]): TransactionRequest = {
    createAuthorizeOrSaleRequest(
      transactionType = TransactionTypes.auth,
      merchant = merchant,
      currencyAmount = currencyAmount,
      creditCard = creditCard,
      deal = deal)
  }

  def createSaleRequest(merchant: PaymentexpressMerchant, currencyAmount: CurrencyAmount,
                        creditCard: CreditCard, deal: Option[Deal]): TransactionRequest = {
    createAuthorizeOrSaleRequest(
      transactionType = TransactionTypes.purchase,
      merchant = merchant,
      currencyAmount = currencyAmount,
      creditCard = creditCard,
      deal = deal)
  }

  private def createAuthorizeOrSaleRequest(transactionType: String, merchant: PaymentexpressMerchant, currencyAmount: CurrencyAmount,
                                   creditCard: CreditCard, deal: Option[Deal]): TransactionRequest = {
    val dealTitle: Option[String] = if (deal.isDefined) {
      deal.get.title
    } else {
      None
    }
    createTransactionRequest(
      transactionType = transactionType,
      merchant = merchant,
      currencyAmount = currencyAmount,
      creditCard = Some(creditCard),
      dealTitle = dealTitle)
  }

  def createCaptureRequest(merchant: PaymentexpressMerchant, currencyAmount: CurrencyAmount,
                           transactionId: String): TransactionRequest = {
    createTransactionRequest(
      transactionType = TransactionTypes.complete,
      merchant = merchant,
      currencyAmount = CurrencyAmount(currencyAmount.currency, currencyAmount.amount),
      transactionId = Some(transactionId))
  }

  private def createTransactionRequest(transactionType: String, merchant: PaymentexpressMerchant, currencyAmount: CurrencyAmount,
                                       creditCard: Option[CreditCard] = None, dealTitle: Option[String] = None,
                                       transactionId: Option[String] = None): TransactionRequest = {
    val request = new TransactionRequest
    request.PostUsername = merchant.username
    request.PostPassword = merchant.password
    request.TxnType = transactionType
    request.InputCurrency = currencyAmount.currency
    request.Amount = new DecimalFormat("0.00").format(currencyAmount.amount)


    creditCard foreach (creditCard => {
      request.CardNumber = creditCard.number
      request.DateExpiry = f"${creditCard.expiration.month}%02d${creditCard.expiration.year - 2000}%02d"

      creditCard.holderName foreach (holderName => request.CardHolderName = holderName)

      if (creditCard.csc.isDefined) {
        request.Cvc2Presence = Cvc2Presences.submitted
        request.Cvc2 = creditCard.csc.get
      } else {
        request.Cvc2Presence = Cvc2Presences.notSubmitted
      }

      if (creditCard.billingAddress.isDefined || creditCard.billingPostalCode.isDefined) {
        request.EnableAvsData = Booleans.`true`
        request.AvsAction = AvsActions.checkBestEffort

        creditCard.billingAddress foreach (billingAddress => request.AvsStreetAddress = billingAddress)
        creditCard.billingPostalCode foreach (billingPostalCode => request.AvsPostCode = billingPostalCode)
      } else {
        request.EnableAvsData = Booleans.`false`
      }
    })

    dealTitle foreach (dealTitle => request.MerchantReference = dealTitle)
    transactionId foreach (transactionId => request.DpsTxnRef = transactionId)

    request
  }
}
