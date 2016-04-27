package com.wix.pay.paymentexpress

trait PaymentexpressAuthorizationParser {
  def parse(authorizationKey: String): PaymentexpressAuthorization
  def stringify(authorization: PaymentexpressAuthorization): String
}
