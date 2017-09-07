package elcurto99.scalaquestrade.http

import scala.collection.mutable
import scalaj.http.{Http, HttpResponse}

/**
  * Trait to abstract the making of a HTTP request so we can easily unit test
  */
trait HttpClient {

  def makeRequest(url: String, accessTokenOption: Option[String]): HttpResponse[String] = {
    val request = accessTokenOption match {
      case Some(accessToken) => Http(s"$url").header("Authorization", s"Bearer $accessToken")
      case None              => Http(s"$url")
    }

    request.asString
  }
}

trait TestableHttpClient extends HttpClient {

  var nextResponse: HttpResponse[String] = _
  val requestUrls: mutable.Queue[String] = mutable.Queue[String]()
  def lastUrl: String = requestUrls.last

  override def makeRequest(url: String, accessTokenOption: Option[String]): HttpResponse[String] = {
    requestUrls += url

    nextResponse
  }
}