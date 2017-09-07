package elcurto99.scalaquestrade

import java.time.{LocalDateTime, ZonedDateTime}

import elcurto99.scalaquestrade.models.OrderStateFilterType.OrderStateFilterType
import elcurto99.scalaquestrade.models._

/**
  * Definition of the Questrade API interface
  */
trait QuestradeAPI {

  /**
    * Perform a login request to get an access token for an API server
    * @see http://www.questrade.com/api/documentation/security
    */
  def login(loginDomain: String, refreshToken: String): Login

  /**
    * Get the current server time in ISO format and Eastern time zone
    * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/time
    */
  def getTime(accessToken: String, apiServer: String): ZonedDateTime

  /**
    * Retrieves the accounts associated with the user on behalf of which the API client is authorized
    * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/accounts
    */
  def getAccounts(accessToken: String, apiServer: String): AccountsResponse

  /**
    * Retrieves positions in a specified account
    * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-positions
    */
  def getAccountPositions(accessToken: String, apiServer: String, accountNumber: String): List[Position]

  /**
    * Retrieves positions in a specified account
    * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-balances
    */
  def getAccountBalances(accessToken: String, apiServer: String, accountNumber: String): BalancesResponse

  /**
    * Retrieves executions in a specified account in the defined time range
    * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-executions
    */
  def getAccountExecutions(accessToken: String, apiServer: String, accountNumber: String, startDateTimeOption: Option[LocalDateTime], endTimeOption: Option[LocalDateTime]): List[Execution]

  /**
    * Retrieves orders for a specified account
    * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-orders
    */
  def getAccountOrders(accessToken: String, apiServer: String, accountNumber: String, startDateTimeOption: Option[LocalDateTime], endTimeOption: Option[LocalDateTime], stateFilterOption: Option[OrderStateFilterType], orderIdsList: List[Int]): List[Order]

  /**
    * Retrieves an order for a specified account
    * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-orders
    */
  def getAccountOrder(accessToken: String, apiServer: String, accountNumber: String, orderId: Int): Order

  /**
    * Retrieve account activities, including cash transactions, dividends, trades, etc.
    * Due to this endpoints limit of 31 days of data can be requested at a time, requests for larger ranges of data will be broken up and consume more API calls
    * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-activities
    */
  def getAccountActivities(accessToken: String, apiServer: String, accountNumber: String, startDateTime: LocalDateTime, endDateTimeOption: Option[LocalDateTime]): List[Activity]
}