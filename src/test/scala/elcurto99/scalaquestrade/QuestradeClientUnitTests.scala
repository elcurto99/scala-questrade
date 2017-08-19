package elcurto99.scalaquestrade

import java.time._

import elcurto99.scalaquestrade.http.TestableHttpClient
import elcurto99.scalaquestrade.models.OrderStateFilterType
import org.scalatest.{Matchers, WordSpecLike}

import scala.io.Source
import scalaj.http.HttpResponse


/**
  * Unit tests for the Questrade Client
  */
class QuestradeClientUnitTests extends WordSpecLike with Matchers {

  val loginDomain = "https://login.questrade.com/"
  val testApiUrl = "https://api01.iq.questrade.com/"
  val testAccountNumber = "26598145"
  val testRefreshToken = "IZ94yQ87GQNG5TQrnWMhZKh3FwDMaWmo0"
  val testAccessToken = "qHUAKfM3re_D8sMdGAi9d9zMlbS53MI80"
  val testClient = new TestableQuestradeClient(testRefreshToken)

  "The Questrade API client" should {

    "authenticate with the server" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Login.json")).mkString, 200, Map())

      val loginResponse = testClient.login(loginDomain, testRefreshToken)

      testClient.lastUrl should be (s"https://login.questrade.com/oauth2/token?grant_type=refresh_token&refresh_token=$testRefreshToken")
      loginResponse.access_token should be ("p4VTj45GhS8lY7aFoKDNZxB8yQHMOr+f")
      loginResponse.token_type should be ("Bearer")
      loginResponse.expires_in should be (1800)
      loginResponse.refresh_token should be ("aSBe7wAAdx88QTbwut0tiu3SYic3ox8F")
      loginResponse.api_server should be (testApiUrl)
    }

    "retrieve the current server time" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Time.json")).mkString, 200, Map())

      val currentServerTime = testClient.getTime(testAccessToken, testApiUrl)

      testClient.lastUrl should be (s"${testApiUrl}v1/time")
      currentServerTime.toString should be ("2014-10-24T12:14:42.730-04:00")
    }

    "retrieve the accounts list" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Accounts.json")).mkString, 200, Map())

      val accountsList = testClient.getAccounts(testAccessToken, testApiUrl)

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts")
      accountsList.userId should be (123456)
    }

    "retrieve the positions for an account" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Positions.json")).mkString, 200, Map())

      testClient.getAccountPositions(testAccessToken, testApiUrl, testAccountNumber)

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts/$testAccountNumber/positions")
    }

    "retrieve the balances for an account" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Balances.json")).mkString, 200, Map())

      val balances = testClient.getAccountBalances(testAccessToken, testApiUrl, testAccountNumber)

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts/$testAccountNumber/balances")
      balances.perCurrencyBalances should have size 2
      balances.combinedBalances should have size 2
      balances.sodPerCurrencyBalances should have size 2
      balances.sodCombinedBalances should have size 2
    }

    "retrieve the executions that occurred today for an account" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Executions.json")).mkString, 200, Map())

      testClient.getAccountExecutions(testAccessToken, testApiUrl, testAccountNumber, None, None)

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts/$testAccountNumber/executions")
    }

    "retrieve the executions that occurred from a date for an account" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Executions.json")).mkString, 200, Map())

      testClient.getAccountExecutions(testAccessToken, testApiUrl, testAccountNumber, Some(ZonedDateTime.parse("2017-01-01T00:00-04:00")), None)

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts/$testAccountNumber/executions?startTime=2017-01-01T00:00-04:00")
    }

    "retrieve the executions that occurred in a date range for an account" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Executions.json")).mkString, 200, Map())

      testClient.getAccountExecutions(testAccessToken, testApiUrl, testAccountNumber, Some(ZonedDateTime.parse("2017-01-01T00:00-04:00")), Some(ZonedDateTime.parse("2017-12-31T23:59:59.999999-04:00")))

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts/$testAccountNumber/executions?startTime=2017-01-01T00:00-04:00&endTime=2017-12-31T23:59:59.999999-04:00")
    }

    "retrieve the orders the occurred today for an account" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Orders.json")).mkString, 200, Map())

      testClient.getAccountOrders(testAccessToken, testApiUrl, testAccountNumber, None, None, None, List())

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts/$testAccountNumber/orders")
    }

    "retrieve the orders that occurred from a date for an account" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Orders.json")).mkString, 200, Map())

      testClient.getAccountOrders(testAccessToken, testApiUrl, testAccountNumber, Some(ZonedDateTime.parse("2017-01-01T00:00-04:00")), None, None, List())

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts/$testAccountNumber/orders?startTime=2017-01-01T00:00-04:00")
    }

    "retrieve the orders that occurred in a date range for an account" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Orders.json")).mkString, 200, Map())

      testClient.getAccountOrders(testAccessToken, testApiUrl, testAccountNumber, Some(ZonedDateTime.parse("2017-01-01T00:00-04:00")), Some(ZonedDateTime.parse("2017-12-31T23:59:59.999999-04:00")), None, List())

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts/$testAccountNumber/orders?startTime=2017-01-01T00:00-04:00&endTime=2017-12-31T23:59:59.999999-04:00")
    }

    "retrieve the orders of a specific state for an account" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Orders.json")).mkString, 200, Map())

      testClient.getAccountOrders(testAccessToken, testApiUrl, testAccountNumber, None, None, Some(OrderStateFilterType.Open), List())

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts/$testAccountNumber/orders?stateFilter=Open")
    }

    "retrieve specific orders by orderId for an account" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Orders.json")).mkString, 200, Map())

      testClient.getAccountOrders(testAccessToken, testApiUrl, testAccountNumber, None, None, None, List(173577870, 173577871))

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts/$testAccountNumber/orders?ids=173577870,173577871")
    }

    "retrieve a single order for an account" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Order.json")).mkString, 200, Map())

      val order = testClient.getAccountOrder(testAccessToken, testApiUrl, testAccountNumber, 173577870)

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts/$testAccountNumber/orders/173577870")
      order.id should be (173577870)
    }

    "retrieve the activities that occurred from a date for an account" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Activities.json")).mkString, 200, Map())

      testClient.getAccountActivities(testAccessToken, testApiUrl, testAccountNumber, ZonedDateTime.of(LocalDate.now().withDayOfMonth(1), LocalTime.MIN, ZoneId.from(ZoneOffset.of("-04:00"))), None)

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts/$testAccountNumber/activities?startTime=${LocalDate.now().withDayOfMonth(1)}T00:00-04:00&endTime=${LocalDate.now()}T23:59:59.999999999-04:00")
    }

    "retrieve the activities that occurred in a date range for an account" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Activities.json")).mkString, 200, Map())

      testClient.getAccountActivities(testAccessToken, testApiUrl, testAccountNumber, ZonedDateTime.parse("2017-01-01T00:00-04:00"), Some(ZonedDateTime.parse("2017-01-31T23:59:59.999999-04:00")))

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts/$testAccountNumber/activities?startTime=2017-01-31T00:00-04:00&endTime=2017-01-31T23:59:59.999999-04:00")
    }

    "retrieve activities that occurred over the API maximum of 31 days for an account" in {
      testClient.nextResponse = HttpResponse(Source.fromInputStream(this.getClass.getResourceAsStream("/" + "Activities.json")).mkString, 200, Map())

      testClient.getAccountActivities(testAccessToken, testApiUrl, testAccountNumber, ZonedDateTime.parse("2017-01-01T00:00-04:00"), Some(ZonedDateTime.parse("2017-12-31T23:59:59.999999-04:00")))

      testClient.lastUrl should be (s"${testApiUrl}v1/accounts/$testAccountNumber/activities?startTime=2017-12-27T00:00-04:00&endTime=2017-12-31T23:59:59.999999-04:00")
    }
  }
}

class TestableQuestradeClient(refreshToken: String) extends QuestradeClient(refreshToken = refreshToken) with TestableHttpClient {}