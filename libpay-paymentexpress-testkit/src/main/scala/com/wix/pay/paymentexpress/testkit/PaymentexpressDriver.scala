package com.wix.pay.paymentexpress.testkit

import com.wix.hoopoe.http.testkit.EmbeddedHttpProbe
import com.wix.pay.paymentexpress.model.{TransactionRequest, TransactionResponse}
import com.wix.pay.paymentexpress.{TransactionRequestParser, TransactionResponseParser}
import spray.http._

class PaymentexpressDriver(probe: EmbeddedHttpProbe) {
  def this(port: Int) = this(new EmbeddedHttpProbe(port, EmbeddedHttpProbe.NotFoundHandler))

  private val transactionRequestParser = new TransactionRequestParser
  private val transactionResponseParser = new TransactionResponseParser

  def startProbe() {
    probe.doStart()
  }

  def stopProbe() {
    probe.doStop()
  }

  def resetProbe() {
    probe.handlers.clear()
  }

  def aRequestFor(request: TransactionRequest): RequestCtx = {
    new RequestCtx(Some(request))
  }

  def anyRequest(): RequestCtx = {
    new RequestCtx(None)
  }

  class RequestCtx(request: Option[TransactionRequest]) {
    def returns(response: TransactionResponse) {
      probe.handlers += {
        case HttpRequest(
        HttpMethods.POST,
        Uri.Path("/"),
        _,
        entity,
        _) if isStubbedRequestEntity(entity) =>
          HttpResponse(
            status = StatusCodes.OK,
            entity = HttpEntity(ContentType(MediaTypes.`application/xml`), transactionResponseParser.stringify(response)))
      }
    }

    private def isStubbedRequestEntity(entity: HttpEntity): Boolean = {
      lazy val parsedRequest = transactionRequestParser.parse(entity.asString)
      request.forall(_ == parsedRequest)
    }
  }
}
