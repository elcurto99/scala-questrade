package elcurto99.scalaquestrade

import java.time._

import elcurto99.scalaquestrade.exceptions.QuestradeApiError
import elcurto99.scalaquestrade.http.HttpClient
import elcurto99.scalaquestrade.models.OrderStateFilterType.OrderStateFilterType
import elcurto99.scalaquestrade.models._
import org.json4s.ext.EnumNameSerializer
import org.json4s.jackson.JsonMethods.parse
import org.json4s.{DefaultFormats, Formats}
import org.slf4j.{Logger, LoggerFactory}

import scala.util.{Failure, Success, Try}
import scalaj.http.HttpResponse

/**
  * A Scala based REST client for the Questrade API
  */
class QuestradeClient() extends QuestradeAPI with HttpClient {

  protected val log : Logger = LoggerFactory.getLogger(this.getClass)
  implicit protected val formats: Formats = DefaultFormats + new EnumNameSerializer(Currency) + new EnumNameSerializer(AccountType) + new EnumNameSerializer(AccountStatus) + new EnumNameSerializer(ClientAccountType) + new EnumNameSerializer(OrderSide) + new EnumNameSerializer(OrderType) + new EnumNameSerializer(OrderTimeInForce) + new EnumNameSerializer(OrderState)

  protected def tryAndExtractObject[T](httpResponse: HttpResponse[String], extractionFunction: String => T): T = {
    if (httpResponse.isSuccess) {
      Try {
        extractionFunction(httpResponse.body)
      } match {
        case Success(entity) => entity
        case Failure(throwable) => throw new RuntimeException(s"Error encountered trying to extract entity from response: ${httpResponse.body}", throwable)
      }
    } else {
      val errorObject = parse(httpResponse.body).extract[Error]
      log.error(s"Questrade API Error HTTP: ${httpResponse.code} Code: ${errorObject.code} Message: ${errorObject.message}")
      throw QuestradeApiError(httpResponse.code, errorObject.message, errorObject.code)
    }
  }

  override def login(loginDomain: String, refreshToken: String): Login = {
    val httpResponse = this.makeRequest(s"${loginDomain}oauth2/token?grant_type=refresh_token&refresh_token=$refreshToken", None)

    def extractionFunction(body: String): Login = {
      parse(body).extract[Login]
    }

    this.tryAndExtractObject(httpResponse, extractionFunction)
  }

  override def getTime(accessToken: String, apiServer: String): ZonedDateTime = {
    val httpResponse = this.makeRequest(s"${apiServer}v1/time", Some(accessToken))

    def extractionFunction(body: String): ZonedDateTime = {
      ZonedDateTime.parse(parse(body).extract[Time].time)
    }

    this.tryAndExtractObject(httpResponse, extractionFunction)
  }

  override def getAccounts(accessToken: String, apiServer: String): AccountsResponse = {
    val httpResponse = this.makeRequest(s"${apiServer}v1/accounts", Some(accessToken))

    def extractionFunction(body: String): AccountsResponse = {
      parse(body).extract[AccountsResponse]
    }

    this.tryAndExtractObject(httpResponse, extractionFunction)
  }

  override def getAccountPositions(accessToken: String, apiServer: String, accountNumber: String): List[Position] = {
    val httpResponse = this.makeRequest(s"${apiServer}v1/accounts/$accountNumber/positions", Some(accessToken))

    def extractionFunction(body: String): List[Position] = {
      parse(body).extract[PositionsResponse].positions
    }

    this.tryAndExtractObject(httpResponse, extractionFunction)
  }

  override def getAccountBalances(accessToken: String, apiServer: String, accountNumber: String): BalancesResponse = {
    val httpResponse = this.makeRequest(s"${apiServer}v1/accounts/$accountNumber/balances", Some(accessToken))

    def extractionFunction(body: String): BalancesResponse = {
      parse(body).extract[BalancesResponse]
    }

    this.tryAndExtractObject(httpResponse, extractionFunction)
  }

  override def getAccountExecutions(accessToken: String, apiServer: String, accountNumber: String, startDateTimeOption: Option[LocalDateTime] = None, endDateTimeOption: Option[LocalDateTime] = None): List[Execution] = {
    val url = (startDateTimeOption, endDateTimeOption) match {
      case (Some(startDateTime), None)              => s"${apiServer}v1/accounts/$accountNumber/executions?startTime=${this.getZonedDateTimeForAPI(startDateTime)}"
      case (Some(startDateTime), Some(endDateTime)) => s"${apiServer}v1/accounts/$accountNumber/executions?startTime=${this.getZonedDateTimeForAPI(startDateTime)}&endTime=${this.getZonedDateTimeForAPI(endDateTime)}"
      case _                                        => s"${apiServer}v1/accounts/$accountNumber/executions"
    }

    val httpResponse = this.makeRequest(url, Some(accessToken))

    def extractionFunction(body: String): List[Execution] = {
      parse(body).extract[ExecutionsResponse].executions
    }

    this.tryAndExtractObject(httpResponse, extractionFunction)
  }

  override def getAccountOrders(accessToken: String, apiServer: String, accountNumber: String, startDateTimeOption: Option[LocalDateTime] = None, endDateTimeOption: Option[LocalDateTime] = None, stateFilterOption: Option[OrderStateFilterType] = None, orderIdsList: List[Int] = List()): List[Order] = {
    val url = (startDateTimeOption, endDateTimeOption, stateFilterOption, orderIdsList) match {
      case (_, _, _, `orderIdsList`) if orderIdsList.nonEmpty              => s"${apiServer}v1/accounts/$accountNumber/orders?ids=${orderIdsList.mkString(",")}"
      case (Some(startDateTime), None, None, _)                            => s"${apiServer}v1/accounts/$accountNumber/orders?startTime=${this.getZonedDateTimeForAPI(startDateTime)}"
      case (Some(startDateTime), None, Some(stateFilter), _)               => s"${apiServer}v1/accounts/$accountNumber/orders?startTime=${this.getZonedDateTimeForAPI(startDateTime)}&stateFilter=$stateFilter"
      case (Some(startDateTime), Some(endDateTime), None, _)               => s"${apiServer}v1/accounts/$accountNumber/orders?startTime=${this.getZonedDateTimeForAPI(startDateTime)}&endTime=${this.getZonedDateTimeForAPI(endDateTime)}"
      case (Some(startDateTime), Some(endDateTime), Some(stateFilter), _)  => s"${apiServer}v1/accounts/$accountNumber/orders?startTime=${this.getZonedDateTimeForAPI(startDateTime)}&endTime=${this.getZonedDateTimeForAPI(endDateTime)}&stateFilter=$stateFilter"
      case (None, None, Some(stateFilter), _)                              => s"${apiServer}v1/accounts/$accountNumber/orders?stateFilter=$stateFilter"
      case _                                                               => s"${apiServer}v1/accounts/$accountNumber/orders"
    }

    val httpResponse = this.makeRequest(url, Some(accessToken))

    def extractionFunction(body: String): List[Order] = {
      parse(body).extract[OrdersResponse].orders
    }

    this.tryAndExtractObject(httpResponse, extractionFunction)
  }

  override def getAccountOrder(accessToken: String, apiServer: String, accountNumber: String, orderId: Int): Order = {
    val httpResponse = this.makeRequest(s"${apiServer}v1/accounts/$accountNumber/orders/$orderId", Some(accessToken))

    def extractionFunction(body: String): Order = {
      parse(body).extract[OrdersResponse].orders.head
    }

    this.tryAndExtractObject(httpResponse, extractionFunction)
  }

  override def getAccountActivities(accessToken: String, apiServer: String, accountNumber: String, startDateTime: LocalDateTime, endDateTimeOption: Option[LocalDateTime]): List[Activity] = {

    val convertedEndDateTimeOption = endDateTimeOption match {
      case Some(endDateTime) => Some(this.getZonedDateTimeForAPI(endDateTime))
      case None              => None
    }

    this.getAccountActivities(accessToken, apiServer, accountNumber, this.getZonedDateTimeForAPI(startDateTime), convertedEndDateTimeOption)
  }

  protected def getAccountActivities(accessToken: String, apiServer: String, accountNumber: String, startDateTime: ZonedDateTime, endDateTimeOption: Option[ZonedDateTime]): List[Activity] = {

    if (startDateTime.isAfter(this.getZonedDateTimeForAPI(LocalDateTime.of(LocalDate.now(), LocalTime.MAX)))) {
      throw new IllegalArgumentException("The startDateTime must occur before the current date/time.")
    }

    val endDateTime = endDateTimeOption match {
      case None           => this.getZonedDateTimeForAPI(LocalDateTime.of(LocalDate.now(), LocalTime.MAX))
      case Some(dateTime) => dateTime
    }

    val oneMonthAfterStart = this.getZonedDateTimeForAPI(startDateTime.plusDays(30).minusNanos(1).toLocalDateTime)

    if (oneMonthAfterStart.isBefore(endDateTime)) {
      val firstList = this.requestAccountActivities(accessToken, apiServer, accountNumber, startDateTime, oneMonthAfterStart)
      val remainingList = this.getAccountActivities(accessToken, apiServer, accountNumber, oneMonthAfterStart.plusNanos(1), Some(endDateTime))

      firstList ++ remainingList
    } else {
      this.requestAccountActivities(accessToken, apiServer, accountNumber, startDateTime, endDateTime)
    }
  }

  protected def requestAccountActivities(accessToken: String, apiServer: String, accountNumber: String, startDateTime: ZonedDateTime, endDateTime: ZonedDateTime): List[Activity] = {
    val url = s"${apiServer}v1/accounts/$accountNumber/activities?startTime=$startDateTime&endTime=$endDateTime"

    val httpResponse = this.makeRequest(url, Some(accessToken))

    def extractionFunction(body: String): List[Activity] = {
      parse(body).extract[ActivitiesResponse].activities
    }

    this.tryAndExtractObject(httpResponse, extractionFunction)
  }

  protected def getZonedDateTimeForAPI(dateTime: LocalDateTime): ZonedDateTime = {
    ZonedDateTime.of(dateTime, ZoneId.of("America/Toronto").getRules.getOffset(dateTime))
  }
}