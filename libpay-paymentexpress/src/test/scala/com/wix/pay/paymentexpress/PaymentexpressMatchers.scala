package com.wix.pay.paymentexpress

import org.specs2.matcher.{AlwaysMatcher, Matcher, Matchers}

trait PaymentexpressMatchers extends Matchers {
  def authorizationParser: PaymentexpressAuthorizationParser

  def beAuthorization(currency: Matcher[String] = AlwaysMatcher(),
                      dpsTxnRef: Matcher[String] = AlwaysMatcher()): Matcher[PaymentexpressAuthorization] = {
    currency ^^ { (_: PaymentexpressAuthorization).currency aka "currency" } and
      dpsTxnRef ^^ { (_: PaymentexpressAuthorization).dpsTxnRef aka "dpsTxnRef" }
  }

  def beAuthorizationKey(authorization: Matcher[PaymentexpressAuthorization]): Matcher[String] = {
    authorization ^^ { authorizationParser.parse(_: String) aka "parsed authorization"}
  }

  def beMerchant(username: Matcher[String] = AlwaysMatcher(),
                 password: Matcher[String] = AlwaysMatcher()): Matcher[PaymentexpressMerchant] = {
    username ^^ { (_: PaymentexpressMerchant).username aka "username" } and
      password ^^ { (_: PaymentexpressMerchant).password aka "password" }
  }
}

object PaymentexpressMatchers extends PaymentexpressMatchers {
  override val authorizationParser = new JsonPaymentexpressAuthorizationParser()
}