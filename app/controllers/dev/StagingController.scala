package controllers.dev

import play.api.mvc._

import java.io.File
import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class StagingController @Inject() (
  val controllerComponents: ControllerComponents
) extends BaseController {

  def at(filepath: String): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      val basepath = "creatives/"
      try {
        Ok.sendFile(new File(basepath + filepath))
      } catch {
        case e: Throwable =>
          println(e)
          Status(404)("404 Not found.")
      }
  }
}
