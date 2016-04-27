package com.wix.pay.paymentexpress.model

object AvsActions {
  /** Don't check AVS details with acquirer, but pass them through to Payment Express only. */
  val noCheck = 0
  /**
   * Attempt AVS check. If the acquirer doesn't support AVS or is unavailable, then transaction will proceed as normal.
   * If AVS is supported it will check the transaction and give the result.
   */
  val checkBestEffort = 1
  /** The transactions needs to be checked by AVS, even if isn't available, otherwise the transaction will be blocked. */
  val checkAlways = 2
  /**
   * AVS check will be attempted and any outcome will be recorded, but ignored i.e. transaction will not be declined
   * if AVS fails or unavailable.
   */
  val checkAndIgnore = 3
}
