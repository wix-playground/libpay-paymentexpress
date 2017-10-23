package com.wix.pay.paymentexpress.model

/**
  * @see <a href="https://www.paymentexpress.com/developer-integrated-eftpos-responsecodes">PxPost Integration Guide</a>
  * @see <a href="http://www.paymentexpress.com/Document/PxPost_Integration_Guide.pdf"></a>
  */
object ErrorCodes {
  /** No such user for PXPost. Please contact Payment Express to confirm your account information. */
  val noSuchUser = "D2"
  /** Blank password for PX Post. Please contact Payment Express to confirm your account information. */
  val blankPassword = "D3"
  /** Invalid Password for PxPost. Please contact Payment Express to confirm your account information. */
  val invalidPassword = "D5"
  val authenticationError = "D9"
  /** Amount value is not of the form d.cc where d is dollars and cc is cents value. */
  val invalidAmount = "A8"
  /** Card number not a valid credit card number. */
  val invalidCardNumber = "A9"
  /** An incorrect account was selected. */
  val invalidAccount = "SA"
  /** Expiry not in MMYY format or Month not between "01" and "12". */
  val invalidExpiry = "AB"
  /** Local expiry check failed. Card is expired or local computer time is incorrect. */
  val cardExpired = "AC"
  /** An incorrect account was selected. */
  val accountError = "AD"
  /** Timeout awaiting bank response - transaction is declined. */
  val bankTimeout = "AE"
  /**
   * Only possible for a DoStatus call. Indicates Payment Express Server has no record of the transaction and it can
   * be treated as "failed" or may be safely retried.
   */
  val transmissionError = "AF"
  /** Invalid Transaction Type. See TxnType for legal values. */
  val ivlReqType = "B1"
  /** TxnClass not one of list described in TxnClass property. */
  val invalidClientType = "R0"
  /** Communications error. */
  val timeout = "X1"
}
