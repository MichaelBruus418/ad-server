package controllers.api

import play.api.mvc._
import dao.PublisherDao
import play.api.libs.json.Json
import scala.util.{Failure, Random, Success, Try}

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

class PublisherController @Inject() (
                                      val controllerComponents: ControllerComponents,
                                      val publisherDao: PublisherDao,
) extends BaseController {

  def getAll: Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val eventualPublishers = publisherDao.getAll()
      eventualPublishers
        .map(v => Ok(Json.toJson(v)))
        .recover(e => InternalServerError("Ooops... Something went wrong: " + e))
  }

  def get(id: Int): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok("{property: 'value'}").as("application/json")
  }

  def add(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      val post = request.body.asFormUrlEncoded
      post
        .map(args => {
          val name = args("name").head.trim
          Ok("Name recvieved: " + name)
        })
        .getOrElse(Ok("OOPS... Invalid request"))
  }

  def update(id: Int): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok("TODO")
  }

  def delete(id: Int): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val eventualNumOfRowsDeleted = publisherDao.delete(id)
      eventualNumOfRowsDeleted
        .map(v => Ok(v.toString))
        .recover(e => InternalServerError("Ooops... Something went wrong: " + e))

  }

}
