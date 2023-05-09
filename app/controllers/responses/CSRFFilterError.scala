/*
// https://semisafe.com/coding/2015/08/03/play_basics_csrf_protection.html
package controllers.responses

import play.api.mvc._
import play.api.mvc.Results.Forbidden
import play.api.http.Status
import play.api.libs.json.{Format, Json}

import scala.concurrent.Future
import play.filters.csrf.CSRF.ErrorHandler


class CSRFFilterError extends ErrorHandler {

  case class ErrorResponse(
    status: Int,
    message: String,
  )

  private object ErrorResponse {
    implicit val format: Format[ErrorResponse] =
      Json.format[ErrorResponse] // Combined read/write
  }

  def handle(req: RequestHeader, msg: String): Future[Result] = {
    val response = ErrorResponse(Status.FORBIDDEN, msg)
    val result   = Forbidden(Json.toJson(response))
    Future.successful(result)
  }
}
*/
