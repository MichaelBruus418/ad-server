package utils

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
    // Hard-coded credentials for the time being.
    // ..and no, passwords shouldn't be stored in plain-text,
    // but this is only a proof-of-concept Ad-Server, so chill!
    val credentials: Map[String, String] = Map(
      "Jyllands-Posten" -> "1234",
      "Finans"          -> "5678",
    )

    // Hack to ignore case in map keys
    val index = credentials.keys
      .map(_.toLowerCase())
      .toIndexedSeq
      .indexOf(userName.toLowerCase())
    if (
      index != -1 && password.equals(credentials.values.toIndexedSeq(index))
    ) true
    else false

    // Why doesnt this return an Option[String)?
    // val password = credentials.getOrElse(userName, credentials.get(userName.toLowerCase()))
  }
}
