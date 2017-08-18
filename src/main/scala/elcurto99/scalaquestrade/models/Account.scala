package elcurto99.scalaquestrade.models

import elcurto99.scalaquestrade.models.AccountStatus.AccountStatus
import elcurto99.scalaquestrade.models.AccountType.AccountType
import elcurto99.scalaquestrade.models.ClientAccountType.ClientAccountType

/**
  * Models the response from an Accounts request
  *
  * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/accounts
  */
case class AccountsResponse (
  accounts: List[Account],
  userId: Int
)

case class Account (
 `type`: AccountType,
 number: String,
 status: AccountStatus,
 isPrimary: Boolean,
 isBilling: Boolean,
 clientAccountType: ClientAccountType
)