package com.wix.pay.paymentexpress

trait PaymentexpressMerchantParser {
  def parse(merchantKey: String): PaymentexpressMerchant
  def stringify(merchant: PaymentexpressMerchant): String
}
