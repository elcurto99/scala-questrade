package elcurto99.scalaquestrade.models

/**
  * Models the response from a Login request
  * @see http://www.questrade.com/api/documentation/security
  */
case class Login(
  access_token: String,
  token_type: String,
  expires_in: Int,
  refresh_token: String,
  api_server: String
)

