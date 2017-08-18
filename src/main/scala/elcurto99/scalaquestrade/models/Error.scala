package elcurto99.scalaquestrade.models

/**
  * Models the error response from a request
  * @see http://www.questrade.com/api/documentation/error-handling
  */
case class Error(
  code: String,
  message: String
)

