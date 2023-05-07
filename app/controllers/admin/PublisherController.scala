package controllers.admin

import play.api.mvc._
import services.PublisherService

import javax.inject._

@Singleton
class PublisherController @Inject() (
  val controllerComponents: ControllerComponents,
  val publisherService: PublisherService,
) extends BaseController {

  def display(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>

      publisherService.getAll()

      Ok(views.html.admin.publisher.display())
  }

  def edit(id: Int): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      // id == 0: New publisher
      // id > 0: Edit publisher
      Ok(views.html.admin.publisher.edit(id))
  }

}
