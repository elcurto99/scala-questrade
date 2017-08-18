package elcurto99.scalaquestrade.exceptions

/**
  * Object to represent an error for the Questrade API
  *
  * @see http://www.questrade.com/api/documentation/error-handling
  */
case class QuestradeApiError(httpStatusCode: Int, message: String, questradeErrorCode: String) extends Exception {

}