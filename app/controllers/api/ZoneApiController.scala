package controllers.api

import dao.ZoneDao
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

class ZoneApiController @Inject()(
                                      val controllerComponents: ControllerComponents,
                                      val zoneDao: ZoneDao,
) extends BaseController {

  def getAll: Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val eventualPublishers = zoneDao.getAll()
      eventualPublishers
        .map(v => Ok(Json.toJson(v)))
        .recover(e => InternalServerError("Ooops... Something went wrong: " + e))
  }

  def get(id: Int): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok("TODO")
  }

  def getByPublisherId(publisherId: Int): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val eventualZones = zoneDao.getByPublisherId(publisherId)
      eventualZones
        .map(v => Ok(Json.toJson(v)))
        .recover(e => InternalServerError("Ooops... Something went wrong: " + e))
  }

  def add(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
     Ok("TODO")
  }

  def update(id: Int): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok("TODO")
  }

  def delete(id: Int): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok("TODO")
  }

}
