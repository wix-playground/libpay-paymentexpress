package com.wix.pay.paymentexpress

import org.json4s.DefaultFormats
import org.json4s.native.Serialization

class JsonPaymentexpressMerchantParser extends PaymentexpressMerchantParser {
  private implicit val formats = DefaultFormats

  override def parse(merchantKey: String): PaymentexpressMerchant = {
    Serialization.read[PaymentexpressMerchant](merchantKey)
  }

  override def stringify(merchant: PaymentexpressMerchant): String = {
    Serialization.write(merchant)
  }
}
