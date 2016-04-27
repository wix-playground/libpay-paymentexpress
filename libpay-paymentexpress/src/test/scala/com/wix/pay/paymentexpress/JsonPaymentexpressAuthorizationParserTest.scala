package com.wix.pay.paymentexpress


import com.wix.pay.paymentexpress.PaymentexpressMatchers._
import org.specs2.mutable.SpecWithJUnit
import org.specs2.specification.Scope


class JsonPaymentexpressAuthorizationParserTest extends SpecWithJUnit {
  trait Ctx extends Scope {
    val authorizationParser: PaymentexpressAuthorizationParser = new JsonPaymentexpressAuthorizationParser
  }

  "stringify and then parse" should {
    "yield an authorization similar to the original one" in new Ctx {
      val someAuthorization = PaymentexpressAuthorization(
        currency = "some currency",
        dpsTxnRef = "some dpsTxnRef"
      )

      val authorizationKey = authorizationParser.stringify(someAuthorization)
      authorizationParser.parse(authorizationKey) must beAuthorization(
        currency = ===(someAuthorization.currency),
        dpsTxnRef = ===(someAuthorization.dpsTxnRef)
      )
    }
  }
}
