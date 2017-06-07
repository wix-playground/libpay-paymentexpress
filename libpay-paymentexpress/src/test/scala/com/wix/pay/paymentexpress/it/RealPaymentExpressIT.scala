package com.wix.pay.paymentexpress.it

import com.google.api.client.http.javanet.NetHttpTransport
import com.wix.pay.creditcard.{CreditCard, CreditCardOptionalFields, PublicCreditCardOptionalFields, YearMonth}
import com.wix.pay.model.{CurrencyAmount, Payment}
import com.wix.pay.paymentexpress.PaymentexpressGateway
import org.specs2.mutable.SpecWithJUnit
import org.specs2.specification.Scope

class RealPaymentExpressIT extends SpecWithJUnit {
  skipAll

  "Reat Payment Express" should {
    "create purchase" in new ctx {
      gateway.sale(credentials, creditCard, payment, None, None) must beSuccessfulTry
    }
  }

  trait ctx extends Scope {
    val credentials = """{"username":"******","password":"******"}"""

    val creditCard = CreditCard("4000000000000002", YearMonth(2025,2), Some(CreditCardOptionalFields(csc = Some("123"),
      Some(PublicCreditCardOptionalFields(holderId = Some("John Doe"), holderName = Some("John Doe"))))))
    val payment = Payment(CurrencyAmount("AUD", 202))
    val gateway = new PaymentexpressGateway(new NetHttpTransport().createRequestFactory())
  }
}
