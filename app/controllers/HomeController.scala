package controllers

import javax.inject._
import play.api._
import play.api.libs.json.Json
import play.api.mvc._

import java.time.Instant

@Singleton
class HomeController @Inject() (
  val controllerComponents: ControllerComponents
) extends BaseController {

  def index(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>

    Ok(views.html.home.index("yadi yadi yadi"))
  }

}
