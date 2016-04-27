package com.wix.pay.paymentexpress.testkit

import com.wix.hoopoe.http.testkit.EmbeddedHttpProbe
import com.wix.pay.paymentexpress.model.{TransactionRequest, TransactionResponse}
import com.wix.pay.paymentexpress.{TransactionRequestParser, TransactionResponseParser}
import spray.http._

class PaymentexpressDriver(port: Int) {
  private val probe = new EmbeddedHttpProbe(port, EmbeddedHttpProbe.NotFoundHandler)
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
    new RequestCtx(request)
  }

  class RequestCtx(request: TransactionRequest) {
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
      val parsedRequest = transactionRequestParser.parse(entity.asString)
      request == parsedRequest
    }
  }
}
