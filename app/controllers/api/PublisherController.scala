package controllers.api

import play.api.mvc._
import services.PublisherService

import javax.inject._

class PublisherController @Inject() (
  val controllerComponents: ControllerComponents,
  val publisherService: PublisherService,
)  extends BaseController {

  def getAll: Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
   Ok("TODO")
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
