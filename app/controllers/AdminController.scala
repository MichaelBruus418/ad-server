package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class AdminController @Inject() (
  val controllerComponents: ControllerComponents
) extends BaseController {

  def index(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok(views.html.admin.index())
  }

  def publishers(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok(views.html.admin.publishers())
  }

  def publishersEdit(id: Int): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      // id = 0: New publisher
      // id > 0: Edit publisher
      Ok(views.html.admin.publishers_edit(id))
  }




}
