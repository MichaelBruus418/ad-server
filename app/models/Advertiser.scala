package models

import play.api.libs.json.{Format, Json}

case class Advertiser(
  id: Int = 0,
  name: String,
)

object Advertiser {
  implicit val format: Format[Advertiser] =
    Json.format[Advertiser]

}
