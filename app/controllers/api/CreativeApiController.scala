package controllers.api

import models.daos.CreativeDao
import play.api.libs.json.{JsString, Json}
import play.api.mvc._
import utils.{AuthenticateUtil, CreativeUtil}

import java.io.File
import java.nio.file.NoSuchFileException
import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

class CreativeApiController @Inject() (
  val controllerComponents: ControllerComponents,
  val creativeUtil: CreativeUtil,
)(implicit ec: ExecutionContext)
    extends BaseController {

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

  def request: Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val result = authenticate(request.headers)
      if (result.header.status != 200) {
        Future.successful(result)
      } else {
        val body = request.body.asJson

        try {
          val result = body.map(json => {
            val publisherName = json("publisher").as[JsString].value
            val zoneName      = json("zone").as[JsString].value

            val eventualResultOpt = for {
              creative <- creativeUtil.getCreative(publisherName, zoneName)
            } yield {
              creative.map(c => {
                Ok(s"""{
                  |"serve": "http://localhost:9100/api/creative/serve/${c.hash}/index.html",
                  |"viewableImpression": "http://localhost:9100/api/creative/impression/${c.hash}"
                  |}""".stripMargin).as("application/json")
              })
            }

            eventualResultOpt.map(v =>
              v.getOrElse(BadRequest("I don't care"))
            )
          })

          result.getOrElse(
            Future.successful(BadRequest("Ooops... Request unrecognized :-("))
          )
        } catch {
          case e: Throwable =>
            Future.successful(InternalServerError(e.toString))
        }

      }
  }

  def serve(hash: String, file: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val basePath = "creatives/"

      try {
        val eventualResultOpt = for {
          tupleOpt <- creativeUtil.getIdAndFilepathByHash(hash)
        } yield {
          tupleOpt.map(t => {
            val (id, dir, filename) = t
            try {
              if (file.toLowerCase().endsWith("index.html")) {
                // Serve html file for creative
                // TODO: Inc creative counter for downloaded (use id in tuple)
                Ok.sendFile(new File(basePath + dir + "/" + filename))
              } else {
                // Serve assets for creative
                Ok.sendFile(new File(basePath + dir + "/" + file))
              }
            } catch {
              case e: NoSuchFileException => Status(404)("404: Not found.")
              case e: Throwable => Status(500)("500: Unknown Server Error")
            }
          })
        }

        eventualResultOpt.map(v =>
          v.getOrElse(BadRequest("Request unrecognized."))
        )

      } catch {
        case e: Throwable =>
          Future.successful(InternalServerError(e.toString))
      }
  }

  def impression(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok("TODO")
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
