package controllers



import javax.inject._
import play.api._
import play.api.libs.json.Json
import play.api.mvc._

import java.time.Instant

/** This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject() (
  val controllerComponents: ControllerComponents
) extends BaseController {

  def index(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>




      Ok("bla bla bla mere bka")

    // Ok(views.html.home.index())
  }

}
