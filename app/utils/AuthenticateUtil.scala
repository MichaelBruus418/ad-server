package utils

import configs.GenericConfig
import play.api.mvc.Headers

import java.util.Base64

object AuthenticateUtil {

  /*
   * Authenticatino using Basic Auth.
   * Reqquires Authorization field in request header containg base64 encoded userName and password.
   *  */
  def apiBasicAuthentication(headers: Headers): Boolean = {
    headers
      .get("Authorization")
      .fold(false)(auth => {
        val authValues = auth.split(" ")
        if (
          authValues.length == 2 && authValues.head.equalsIgnoreCase("Basic")
        ) {
          val decoded      = new String(
            Base64.getDecoder.decode(authValues(1).getBytes("utf-8"))
          )
          // As we use semicolon to seperate userName:password, check that exactly one is present.
          val numOfMatches = ("(:)".r).findAllMatchIn(decoded).length
          if (numOfMatches != 1)
            throw new IllegalArgumentException(
              "Authorization string invalid."
            )
          // Split into uswerName and password
          val credentials  = decoded.split(":")
          if (credentials.length == 2) {
            authenticateByNameAndPassword(credentials.head, credentials(1))
          } else {
            false
          }
        } else {
          false
        }
      })
  }

  private def authenticateByNameAndPassword(
    userName: String,
    password: String,
  ): Boolean = {

    val passwordOpt = GenericConfig.getPasswordByUserName(userName)
    passwordOpt.fold(false)(_.equals(password))

  }
}
