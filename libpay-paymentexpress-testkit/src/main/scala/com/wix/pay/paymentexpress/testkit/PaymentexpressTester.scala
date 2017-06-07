package com.wix.pay.paymentexpress.testkit

import com.wix.pay.paymentexpress.model.{Booleans, Transaction, TransactionResponse}

trait PaymentexpressTester {
  def anErrorResponse(code: String, message: String, help: String = "") = {
    val response = new TransactionResponse
    response.Transaction = new Transaction

    response.Success = Booleans.`false`
    response.Transaction.Success = Booleans.`false`

    response.Transaction.StatusRequired = Booleans.`false`

    response.ReCo = code
    response.Transaction.ReCo = code

    response.ResponseText = message
    response.HelpText = help
    response.Transaction.responseText

    response
  }

  private def toBoolean(b: Boolean): Int = {
    b match {
      case true => Booleans.`true`
      case false => Booleans.`false`
    }
  }

  def aSuccessfulResponse(authorized: Boolean,
                          cardHolderResponseText: Option[String] = None,
                          cardHolderResponseDescription: Option[String] = None,
                          dpsTxnRef: Option[String] = None) = {
    val response = new TransactionResponse
    response.Transaction = new Transaction

    response.Success = Booleans.`true`
    response.Transaction.Success = Booleans.`true`

    response.Transaction.StatusRequired = Booleans.`false`

    response.Transaction.Authorized = toBoolean(authorized)

    dpsTxnRef foreach (dpsTxnRef => {
      response.DpsTxnRef = dpsTxnRef
      response.Transaction.DpsTxnRef = dpsTxnRef
    })

    cardHolderResponseText foreach (cardHolderResponseText => response.Transaction.CardHolderResponseText = cardHolderResponseText)
    cardHolderResponseDescription foreach (cardHolderResponseDescription => response.Transaction.CardHolderResponseDescription = cardHolderResponseDescription)

    response
  }
}
