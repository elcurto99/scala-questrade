package elcurto99.scalaquestrade.models

import elcurto99.scalaquestrade.models.OrderSide.OrderSide

/**
  * Models the response from an Account Executions request
  *
  * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-executions
  */
case class ExecutionsResponse (
    executions: List[Execution]
)

case class Execution (
  symbol: String,
  symbolId: Int,
  quantity: Int,
  side: OrderSide,
  price: Double,
  id: Int,
  orderId: Int,
  orderChainId: Int,
  exchangeExecId: String,
  timestamp: String,
  notes: String,
  venue: String,
  totalCost: Double,
  orderPlacementCommission: Double,
  commission: Double,
  executionFee: Double,
  secFee: Double,
  legId: Int,
  canadianExecutionFee: Double,
  parentId: Int
)