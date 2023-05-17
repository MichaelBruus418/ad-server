package models

import play.api.libs.json.{Format, Json}

case class Advertiser(
  id: Int = 0,
  name: String,
)

object Advertiser {
  implicit val format: Format[Advertiser] =
    Json.format[Advertiser] // Combined read/write
  // implicit val bannerReads: Reads[Banner] = Json.reads[Banner] // JsValue to case class obj
  // implicit val bannerWrites: Writes[Banner] = Json.writes[Banner] // Case class obj to JsValue
}
