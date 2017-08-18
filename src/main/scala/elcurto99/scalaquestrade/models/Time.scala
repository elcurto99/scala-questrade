package elcurto99.scalaquestrade.models

/**
  * Models the response from a get time request
  * @see http://www.questrade.com/api/documentation/rest-operations/account-calls/time
  */
case class Time(
  time: String
)