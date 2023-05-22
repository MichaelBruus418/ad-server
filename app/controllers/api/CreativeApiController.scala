package controllers.api

import models.daos.CreativeDao
import play.api.libs.json.JsString
import play.api.mvc._
import utils.{AuthenticateUtil, CreativeUtil}

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

class CreativeApiController @Inject() (
  val controllerComponents: ControllerComponents,
  val creativeUtil: CreativeUtil,
)(implicit ec: ExecutionContext) extends BaseController {

  /*
   * Simple connection test.
   * Returns 200 for authorized access or 400/401/500 for unsuccessful access.
   *  */
  def ping: Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      val result = authenticate(request.headers)
      if (result.header.status != 200) {
        result
      } else {
        Ok("Ping successful.")
      }
  }

  def getLink: Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val result = authenticate(request.headers)
      if (result.header.status != 200) {
        Future.successful(result)
      } else {
        val body = request.body.asJson

        try {
          val result = body.map(json => {
            val publisher  = json("publisher").as[JsString].value
            val zone = json("zone").as[JsString].value
            val creative = creativeUtil.getLink(publisher, zone)

            creative.map(v => Ok(v.toString))
          })
          result.getOrElse(
            Future.successful(BadRequest("Ooops... Request unrecognized :-("))
          )
        } catch {
          case e: Throwable => Future.successful(InternalServerError(e.toString))
        }

      }
  }

  private def authenticate(headers: Headers): Result = {
    try {
      if (AuthenticateUtil.apiBasicAuthentication(headers)) {
        Status(200)
      } else {
        Status(401)("401: Not authorized.").withHeaders(
          "WWW-Authenticate" -> "Basic"
        )
      }
    } catch {
      case e: IllegalArgumentException =>
        Status(400)(s"400: Authorization validation error: ${e.getMessage}")
          .withHeaders("WWW-Authenticate" -> "Basic")
      case e: Throwable                =>
        Status(500)("500: Unknown Server Error").withHeaders(
          "WWW-Authenticate" -> "Basic"
        )
    }
  }
}
