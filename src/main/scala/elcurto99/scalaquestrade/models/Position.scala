package elcurto99.scalaquestrade.models

/**
  * Models the response from an Account Positions request
  *
  * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-positions
  */
case class PositionsResponse (
  positions: List[Position]
)

case class Position (
  symbol: String,
  symbolId: Int,
  openQuantity: Double,
  closedQuantity: Double,
  currentMarketValue: Double,
  currentPrice: Double,
  averageEntryPrice: Double,
  closedPnl: Double,
  openPnl: Double,
  totalCost: Double,
  isRealTime: Boolean,
  isUnderReorg: Boolean
)