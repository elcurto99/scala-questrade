package elcurto99.scalaquestrade.models

import elcurto99.scalaquestrade.models.OrderSide.OrderSide
import elcurto99.scalaquestrade.models.OrderState.OrderState
import elcurto99.scalaquestrade.models.OrderTimeInForce.OrderTimeInForce
import elcurto99.scalaquestrade.models.OrderType.OrderType

/**
  * Models the response from an Account Orders request
  *
  * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-orders
  */
case class OrdersResponse (
  orders: List[Order]
)

case class Order (
  id: Int,
  symbol: String,
  symbolId: Int,
  totalQuantity: Int,
  openQuantity: Int,
  filledQuantity: Option[Int],
  canceledQuantity: Int,
  side: OrderSide,
  orderType: OrderType,
  limitPrice: Option[Double],
  stopPrice: Option[Double],
  isAllOrNone: Boolean,
  isAnonymous: Boolean,
  icebergQuantity: Option[Int],
  minQuantity: Option[Int],
  avgExecPrice: Option[Double],
  lastExecPrice: Option[Double],
  source: String,
  timeInForce: OrderTimeInForce,
  gtdDate: Option[String],
  state: OrderState,
  rejectionReason: String,
  chainId: Int,
  creationTime: String,
  updateTime: String,
  notes: String,
  primaryRoute: String,
  secondaryRoute: String,
  orderRoute: String,
  venueHoldingOrder: String,
  comissionCharged: Option[Double],
  exchangeOrderId: String,
  isSignificantShareHolder: Boolean,
  isInsider: Boolean,
  isLimitOffsetInDollar: Boolean,
  userId: Int,
  placementCommission: Option[Double],
  //legs: [],
  strategyType: String,
  triggerStopPrice: Option[Double],
  orderGroupId: Int,
  orderClass: Option[String],
  isCrossZero: Boolean
)