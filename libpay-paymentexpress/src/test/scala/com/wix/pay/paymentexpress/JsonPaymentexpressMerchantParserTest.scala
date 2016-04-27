package com.wix.pay.paymentexpress

import com.wix.pay.paymentexpress.PaymentexpressMatchers._
import org.specs2.mutable.SpecWithJUnit
import org.specs2.specification.Scope

class JsonPaymentexpressMerchantParserTest extends SpecWithJUnit {
  trait Ctx extends Scope {
    val merchantParser: PaymentexpressMerchantParser = new JsonPaymentexpressMerchantParser
  }

  "stringify and then parse" should {
    "yield a merchant similar to the original one" in new Ctx {
      val someMerchant = PaymentexpressMerchant(
        username = "some username",
        password = "some password"
      )

      val merchantKey = merchantParser.stringify(someMerchant)
      merchantParser.parse(merchantKey) must beMerchant(
        username = ===(someMerchant.username),
        password = ===(someMerchant.password)
      )
    }
  }
}
