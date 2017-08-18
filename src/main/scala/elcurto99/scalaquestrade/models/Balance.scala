package elcurto99.scalaquestrade.models

import elcurto99.scalaquestrade.models.Currency.Currency

/**
  * Models the response from an Account Balances request
  *
  * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/accounts-id-balances
  */
case class BalancesResponse (
  perCurrencyBalances: List[Balance],
  combinedBalances: List[Balance],
  sodPerCurrencyBalances: List[Balance],
  sodCombinedBalances: List[Balance]
)

case class Balance (
  currency: Currency,
  cash: Double,
  marketValue: Double,
  totalEquity: Double,
  buyingPower: Double,
  maintenanceExcess: Double,
  isRealTime: Boolean
)