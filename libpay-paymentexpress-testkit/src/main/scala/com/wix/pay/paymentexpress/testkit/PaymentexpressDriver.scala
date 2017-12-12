package com.wix.pay.paymentexpress.testkit


import akka.http.scaladsl.model.Uri.Path
import akka.http.scaladsl.model._
import com.wix.e2e.http.api.StubWebServer
import com.wix.e2e.http.client.extractors.HttpMessageExtractors._
import com.wix.e2e.http.server.WebServerFactory.aStubWebServer
import com.wix.pay.paymentexpress.model.{TransactionRequest, TransactionResponse}
import com.wix.pay.paymentexpress.{TransactionRequestParser, TransactionResponseParser}


class PaymentexpressDriver(port: Int) {
  private val server: StubWebServer = aStubWebServer.onPort(port).build

  private val transactionRequestParser = new TransactionRequestParser
  private val transactionResponseParser = new TransactionResponseParser

  def start(): Unit = server.start()
  def stop(): Unit = server.stop()
  def reset(): Unit = server.replaceWith()


  def aRequestFor(request: TransactionRequest): RequestCtx = new RequestCtx(Some(request))

  def anyRequest(): RequestCtx = new RequestCtx(None)


  class RequestCtx(request: Option[TransactionRequest]) {
    def returns(response: TransactionResponse) {
      server.appendAll {
        case HttpRequest(
          HttpMethods.POST,
          Path("/"),
          _,
          entity,
          _) if isStubbedRequestEntity(entity) =>
            HttpResponse(
              status = StatusCodes.OK,
              entity = HttpEntity(
                ContentType(MediaTypes.`application/xml`, HttpCharsets.`UTF-8`),
                transactionResponseParser.stringify(response)))
      }
    }

    private def isStubbedRequestEntity(entity: HttpEntity): Boolean = {
      lazy val parsedRequest = transactionRequestParser.parse(entity.extractAsString)
      request.forall(_ == parsedRequest)
    }
  }
}
