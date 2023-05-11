package controllers.admin

import play.api.mvc._
import dao.PublisherDao

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class PublisherController @Inject() (
  val controllerComponents: ControllerComponents,
  val publisherDao: PublisherDao,
)(implicit ec: ExecutionContext)
    extends BaseController {

  def main(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok(views.html.admin.publisher.main())
  }

  def edit(id: Int): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      // id == 0: New publisher
      // id > 0: Edit publisher
      Ok(views.html.admin.publisher.edit(id))
  }

}
