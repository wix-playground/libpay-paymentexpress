package com.wix.pay.paymentexpress.model

object Cvc2Presences {
  /** Merchant have chosen not to submit CVC. */
  val notSubmitted = 0
  /** Merchant have included CVC in the Auth / Purchase. */
  val submitted = 1
  /** Card holder has stated CVC is illegible. */
  val illegible = 2
  /** Card holder has stated CVC is not on the card. */
  val notFound = 9
}
