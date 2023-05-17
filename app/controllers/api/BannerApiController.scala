package controllers.api

import models.daos.ZoneDao
import play.api.libs.json.Json
import play.api.mvc._
import utils.AuthenticateUtil

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

class BannerApiController @Inject() (
  val controllerComponents: ControllerComponents,
  val zoneDao: ZoneDao,
) extends BaseController {

  /*
  * Simple connection test.
  * Returns 200 for authorized access or 400/401/500 for unsuccessful access.
  *  */
  def ping: Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      val result = authenticate(request.headers)
      if (result.header.status != 200) {
        result
      }
      else {
        Ok
      }
  }


  private def authenticate(headers: Headers): Result = {
      try {
        if (AuthenticateUtil.apiBasicAuthentication(headers)) {
          Status(200)
        } else {
          Status(401)("Not authorized.").withHeaders(
            "WWW-Authenticate" -> "Basic"
          )
        }
      } catch {
        case e: IllegalArgumentException =>
          Status(400)(s"Authorization validation error: ${e.getMessage}")
            .withHeaders("WWW-Authenticate" -> "Basic")
        case e: Throwable =>
          Status(500)("Unknown Server Errpr").withHeaders(
            "WWW-Authenticate" -> "Basic"
          )
      }
  }
}
