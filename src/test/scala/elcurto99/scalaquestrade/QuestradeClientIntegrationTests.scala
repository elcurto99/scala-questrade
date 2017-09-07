package elcurto99.scalaquestrade

import java.time._

import com.typesafe.config.ConfigFactory
import elcurto99.scalaquestrade.models._
import org.scalatest.{Matchers, WordSpecLike}

/**
  * Integrations tests for the Questrade Client
  */
class QuestradeClientIntegrationTests extends WordSpecLike with Matchers {

  var testApiUrl: String = _
  var testAccessToken: String = _
  var testAccountNumber: String = _
  var testOrderNumberList: List[Int] = _

  val loginDomain: String = ConfigFactory.load().getString("questrade.api.loginUrl")
  val testRefreshToken: String = ConfigFactory.load().getString("questrade.api.refreshToken")
  val testClient = new QuestradeClient()

  "The Questrade API client" should {

    "authenticate with the server" in {
      val loginResponse = testClient.login(loginDomain, testRefreshToken)

      loginResponse shouldBe a [Login]
      this.testApiUrl = loginResponse.api_server
      this.testAccessToken = loginResponse.access_token
    }

    "retrieve the current server time" in {
      val currentServerTime = testClient.getTime(testAccessToken, testApiUrl)

      currentServerTime shouldBe a [ZonedDateTime]
    }

    "retrieve the accounts list" in {
      val accountsList = testClient.getAccounts(testAccessToken, testApiUrl)

      accountsList shouldBe a [AccountsResponse]
      accountsList.accounts shouldBe a [List[_]]
      accountsList.accounts should not be empty
      accountsList.accounts.head shouldBe a [Account]
      this.testAccountNumber = accountsList.accounts.head.number
    }

    "retrieve the positions for an account" in {
      val positions = testClient.getAccountPositions(testAccessToken, testApiUrl, testAccountNumber)

      positions shouldBe a [List[_]]
      if (positions.nonEmpty) positions.head shouldBe a [Position]
    }

    "retrieve the balances for an account" in {
      val balances = testClient.getAccountBalances(testAccessToken, testApiUrl, testAccountNumber)

      balances shouldBe a [BalancesResponse]
      balances.perCurrencyBalances shouldBe a [List[_]]
      balances.perCurrencyBalances.head shouldBe a [Balance]
      balances.combinedBalances shouldBe a [List[_]]
      balances.combinedBalances.head shouldBe a [Balance]
      balances.sodPerCurrencyBalances shouldBe a [List[_]]
      balances.sodPerCurrencyBalances.head shouldBe a [Balance]
      balances.sodCombinedBalances shouldBe a [List[_]]
      balances.sodCombinedBalances.head shouldBe a [Balance]
    }

    "retrieve the account executions" that {

      "occurred today" in {
        val executions = testClient.getAccountExecutions(testAccessToken, testApiUrl, testAccountNumber, None, None)

        executions shouldBe a [List[_]]
        if (executions.nonEmpty) executions.head shouldBe a [Execution]
      }

      "occurred from a date" in {
        val executions = testClient.getAccountExecutions(testAccessToken, testApiUrl, testAccountNumber, Some(LocalDateTime.parse("2017-01-01T00:00:00.000000")), None)

        executions shouldBe a [List[_]]
        if (executions.nonEmpty) executions.head shouldBe a [Execution]
      }

      "occurred in a date range" in {
        val executions = testClient.getAccountExecutions(testAccessToken, testApiUrl, testAccountNumber, Some(LocalDateTime.parse("2017-01-01T00:00:00.000000")), Some(LocalDateTime.parse("2017-12-31T23:59:59.999999")))

        executions shouldBe a [List[_]]
        if (executions.nonEmpty) executions.head shouldBe a [Execution]
      }

    }

    "retrieve the account orders" that {

      "occurred today" in {
        val orders = testClient.getAccountOrders(testAccessToken, testApiUrl, testAccountNumber, None, None, None, List())

        orders shouldBe a [List[_]]
        if (orders.nonEmpty) orders.head shouldBe a [Order]
      }

      "occurred from a date" in {
        val orders = testClient.getAccountOrders(testAccessToken, testApiUrl, testAccountNumber, Some(LocalDateTime.parse("2017-01-01T00:00:00.000000")), None, None, List())

        orders shouldBe a [List[_]]
        orders.size should be > 2
        orders.head shouldBe a [Order]
        this.testOrderNumberList = List(orders(0).id, orders(1).id)
      }

      "occurred in a date range" in {
        val orders = testClient.getAccountOrders(testAccessToken, testApiUrl, testAccountNumber, Some(LocalDateTime.parse("2017-01-01T00:00:00.000000")), Some(LocalDateTime.parse("2017-12-31T23:59:59.999999")), None, List())

        orders shouldBe a [List[_]]
        if (orders.nonEmpty) orders.head shouldBe a [Order]
      }

      "have a specific state" in {
        val orders = testClient.getAccountOrders(testAccessToken, testApiUrl, testAccountNumber, None, None, Some(OrderStateFilterType.Open), List())

        orders shouldBe a [List[_]]
        if (orders.nonEmpty) orders.head shouldBe a [Order]
      }

      "have a specific orderId" in {
        val orders = testClient.getAccountOrders(testAccessToken, testApiUrl, testAccountNumber, None, None, None, testOrderNumberList)

        orders shouldBe a [List[_]]
        orders.head shouldBe a [Order]
      }

    }

    "retrieve a single order for an account" in {
      val order = testClient.getAccountOrder(testAccessToken, testApiUrl, testAccountNumber, testOrderNumberList.head)

      order shouldBe a [Order]
      order.id should be (testOrderNumberList.head)
    }

    "retrieve the account activities" that {

      "occurred from a date" in {
        val activities = testClient.getAccountActivities(testAccessToken, testApiUrl, testAccountNumber, LocalDateTime.parse("2017-01-01T00:00:00.000000"), None)

        activities shouldBe a [List[_]]
        if (activities.nonEmpty) activities.head shouldBe a [Activity]
      }

      "occurred under the API maximum of 31 days" in {
        val activities = testClient.getAccountActivities(testAccessToken, testApiUrl, testAccountNumber, LocalDateTime.parse("2017-01-01T00:00:00.000000"), Some(LocalDateTime.parse("2017-01-15T23:59:59.999999")))

        activities shouldBe a [List[_]]
        if (activities.nonEmpty) activities.head shouldBe a [Activity]
      }

      "occurred over the API maximum of 31 days" in {
        val activities = testClient.getAccountActivities(testAccessToken, testApiUrl, testAccountNumber, LocalDateTime.parse("2017-01-01T00:00:00.000000"), Some(LocalDateTime.parse("2017-07-31T23:59:59.999999")))

        activities shouldBe a [List[_]]
        if (activities.nonEmpty) activities.head shouldBe a [Activity]
      }

      "occurred over the API maximum of 31 days up until now" in {
        val activities = testClient.getAccountActivities(testAccessToken, testApiUrl, testAccountNumber, LocalDateTime.of(LocalDate.parse("2017-01-01"), LocalTime.MIN), None)

        activities shouldBe a [List[_]]
        if (activities.nonEmpty) activities.head shouldBe a [Activity]
      }

    }
  }
}