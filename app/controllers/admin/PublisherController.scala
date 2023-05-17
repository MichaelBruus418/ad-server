package controllers.admin

import play.api.mvc._
import models.Banner
import models.daos.{BannerDao, PublisherDao}

import java.time.LocalDateTime
import javax.inject._
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Random, Success, Try}

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
