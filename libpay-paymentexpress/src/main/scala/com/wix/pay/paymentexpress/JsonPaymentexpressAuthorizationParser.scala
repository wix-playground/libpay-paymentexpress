package com.wix.pay.paymentexpress

import org.json4s.DefaultFormats
import org.json4s.native.Serialization

class JsonPaymentexpressAuthorizationParser() extends PaymentexpressAuthorizationParser {
  private implicit val formats = DefaultFormats

  override def parse(authorizationKey: String): PaymentexpressAuthorization = {
    Serialization.read[PaymentexpressAuthorization](authorizationKey)
  }

  override def stringify(authorization: PaymentexpressAuthorization): String = {
    Serialization.write(authorization)
  }
}
