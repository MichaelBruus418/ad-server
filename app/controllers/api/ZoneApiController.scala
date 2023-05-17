package controllers.api

import models.daos.ZoneDao
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

class ZoneApiController @Inject() (
  val controllerComponents: ControllerComponents,
  val zoneDao: ZoneDao,
) extends BaseController {

  def getAll: Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val eventualZones = zoneDao.getAll()
      eventualZones
        .map(v => Ok(Json.toJson(v)))
        .recover(e => InternalServerError(e.toString))
  }

  def get(id: Int): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val eventualZone = zoneDao.get(id)
      // Option(none) gets converted to null in json.
      eventualZone
        .map(v => Ok(Json.toJson(v)))
        .recover(e => InternalServerError(e.toString))
  }

  def getByPublisherId(publisherId: Int): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val eventualZones = zoneDao.getByPublisherId(publisherId)
      eventualZones
        .map(v => Ok(Json.toJson(v)))
        .recover(e => InternalServerError(e.toString))
  }

}
