package controllers

import javax.inject._
import play.api._
import play.api.libs.json.Json
import play.api.mvc._

import java.time.Instant
import scala.Console.println

@Singleton
class HomeController @Inject() (
  val controllerComponents: ControllerComponents
) extends BaseController {

  def index(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
    println("Ad-Server: HomeController.index() called.")
    Ok(views.html.home.index("yadi yadi yadi"))
  }

}
