package controllers

import javax.inject._
import play.api._
import play.api.libs.json.Json
import play.api.mvc._

import scala.Console.println

/** This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class ApiController @Inject() (val controllerComponents: ControllerComponents)
    extends BaseController {

  def request(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>

    // val json = request.body.asJson
    // println("json: " + json)


    val foo =
      """
       {
         "name" : "Watership Down",
         "location" : {
           "lat" : 51.235685,
           "long" : -1.309197
         },
         "residents" : [ {
           "name" : "Fiver",
           "age" : 4,
           "role" : null
         }, {
           "name" : "Bigwig",
           "age" : 6,
           "role" : "Owsla"
         } ]
       }
        """

    // Bla bla bla bla
    val bar = Json.parse(foo)

    Ok(bar)
  }

}
