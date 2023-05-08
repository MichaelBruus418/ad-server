package controllers.admin

import play.api.mvc._
import dao.PublisherDAO
import models.Publisher

import javax.inject._

@Singleton
class PublisherController @Inject() (
  val controllerComponents: ControllerComponents,
  val publisherDAO: PublisherDAO,
) extends BaseController {

  def display(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok(views.html.admin.publisher.display())
  }

  def edit(id: Int): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      // id == 0: New publisher
      // id > 0: Edit publisher
      Ok(views.html.admin.publisher.edit(id))
  }

  def add(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      val post = request.body.asFormUrlEncoded
      post
        .map(args => {
          val name = args("name").head.trim
          println("Name recvieved: " + name)
          val p = Publisher(name = name)
          publisherDAO.add(publisher = p)
          Redirect(routes.PublisherController.display())
        })
        .getOrElse(BadRequest("OOPS... Invalid request"))
  }

}
