package controllers.admin

import play.api.mvc._

import javax.inject._

@Singleton
class AdminController @Inject() (
  val controllerComponents: ControllerComponents
) extends BaseController {

  def index(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok(views.html.admin.index())
  }
}
