package controllers.admin

import play.api.mvc._
import dao.PublisherDao
import models.Publisher

import javax.inject._
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Random, Success, Try}

@Singleton
class PublisherController @Inject() (
                                      val controllerComponents: ControllerComponents,
                                      val publisherDao: PublisherDao,
)(implicit ec: ExecutionContext) extends BaseController {

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

  def add(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      val post = request.body.asFormUrlEncoded
      post
        .map(args => {
          val name = args("name").head.trim
          val p = Publisher(name = name)
          val eventualInsertId = publisherDao.add(p)
          eventualInsertId.onComplete {
            case Success(v) => println("Insert id: " + v)
            case Failure(e) => println(e)
          }
          Redirect(routes.PublisherController.main())
        })
        .getOrElse(BadRequest("OOPS... Invalid request"))
  }


}
