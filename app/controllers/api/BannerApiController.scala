package controllers.api

import models.daos.ZoneDao
import play.api.libs.json.Json
import play.api.mvc._
import utils.AuthenticateUtil

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

class BannerApiController @Inject()(
  val controllerComponents: ControllerComponents,
  val zoneDao: ZoneDao,
) extends BaseController {

  def ping: Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      val headers = request.headers.toString()
      val bodyJson = request.body.asJson
      println("Headers:")
      println(headers)
      println("Body:")
      println(bodyJson)

      val result = AuthenticateUtil.apiBasicAuthentication(request.headers)


      Ok(Json.toJson("status: {'ping success'}"))

  }



}
