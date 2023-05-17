package utils

import play.api.libs.Comet.initialByteString.decodeBase64
import play.api.mvc.Headers

import java.util.Base64

object AuthenticateUtil {

  def apiBasicAuthentication(headers: Headers): Boolean = {

    headers
      .get("Authorization")
      .fold(false)(auth => {
        val authValues = auth.split(" ")
        if (
          authValues.length == 2 && authValues.head.equalsIgnoreCase("Basic")
        ) {
          val decoded              = new String(
            Base64.getDecoder.decode(authValues(1).getBytes("utf-8"))
          )
          val credentials          = decoded.split(":")
          val (username, password) = (credentials.head, credentials(1))
          println("username: " + username + " password: " + password)
          false
        } else {
          false
        }
      })
  }

  def authenticateByNameAndPassword(
    username: String,
    password: String,
  ): Boolean = {
    false
  }
}
