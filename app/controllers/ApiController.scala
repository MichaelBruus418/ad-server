package controllers


import javax.inject._
import play.api.mvc._
import services.PublisherService

@Singleton
class ApiController @Inject() (
  val controllerComponents: ControllerComponents,
  val publisherService: PublisherService
)    extends BaseController {

  def publisherAdd(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>

      publisherService.getAll()

      val post = request.body.asFormUrlEncoded
      post
        .map(args => {
          val name = args("name").head.trim
          Ok("Name recvieved: " + name)
        })
        .getOrElse(Ok("OOPS... Invalid request"))

  }

}
