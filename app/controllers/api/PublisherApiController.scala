package controllers.api

import play.api.mvc._
import models.Publisher
import models.daos.PublisherDao
import play.api.libs.json.Json

import javax.inject._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class PublisherApiController @Inject() (
  val controllerComponents: ControllerComponents,
  val publisherDao: PublisherDao,
)(implicit ec: ExecutionContext)
    extends BaseController {

  def getAll: Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val eventualPublishers = publisherDao.getAll()
      eventualPublishers
        .map(v => Ok(Json.toJson(v)))
        .recover(e => InternalServerError(e.toString))
  }

  def get(id: Int): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val eventualPublisher = publisherDao.get(id)
      // Option(none) gets converted to null in json.
      eventualPublisher
        .map(v => Ok(Json.toJson(v)))
        .recover(e => InternalServerError(e.toString))
  }

  def add(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      // --- Version 01 ---------------------------------------------------
      val postOpt             = request.body.asFormUrlEncoded
      val eventualInsertIdOpt = postOpt.map(args => {
        val name = args("name").head.trim
        val p    = Publisher(name = name)
        publisherDao.add(p)
      })

      eventualInsertIdOpt match {
        case Some(f) =>
          f.map(v => Ok(v.toString))
            .recover(e => InternalServerError(e.toString))
        case None    =>
          Future.successful(
            InternalServerError("Ooops... Something went wrong :-(")
          )
      }

      //  --- Version 02 -------------------------------------------------
      /*   val resultOpt = for {
        eventualInsertId <- request.body.asFormUrlEncoded.map(args => {
          val name = args("name").head.trim
          val p    = Publisher(name = name)
          publisherDao.add(p)
        })
      } yield {
        eventualInsertId
          .map(v => Ok(v.toString))
          .recover(e => InternalServerError(e.toString))
      }

      resultOpt.getOrElse(
        Future.successful(
          InternalServerError("Ooops... Something went wrong :-(")
        )
      ) */
  }

  def update(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok("TODO")
  }

  def delete(id: Int): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val eventualNumOfRowsDeleted = publisherDao.delete(id)
      eventualNumOfRowsDeleted
        .map(v => Ok(v.toString))
        .recover(e => InternalServerError(e.toString))
  }

}
