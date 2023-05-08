package controllers.api

import play.api.mvc._
import dao.PublisherDAO
import play.api.libs.json.Json

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

class PublisherController @Inject() (
  val controllerComponents: ControllerComponents,
  val publisherDAO: PublisherDAO,
) extends BaseController {

  def getAll: Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val eventualPublishers = publisherDAO.getAll()
      eventualPublishers
        .map(v => Ok(Json.toJson(v)).as("application/json"))
        .recover(e => BadRequest("Ooos... Something went wrong: " + e))
  }

  def get(id: Int): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok("TODO")
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

  def delete(id: Int): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok("TODO")
  }

}
