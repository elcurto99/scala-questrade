package elcurto99.scalaquestrade.models

import elcurto99.scalaquestrade.models.Currency.Currency

/**
  * Models the response from an Account Orders request
  *
  * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-orders
  */
case class ActivitiesResponse (
  activities: List[Activity]
)

case class Activity (
  tradeDate: String,
  transactionDate: String,
  settlementDate: String,
  action: String,
  symbol: String,
  symbolId: Int,
  description: String,
  currency: Currency,
  quantity: Double,
  price: Double,
  grossAmount: Double,
  commission: Double,
  netAmount: Double,
  `type`: String
)