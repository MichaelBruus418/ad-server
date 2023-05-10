package models

import play.api.libs.json.{Format, Json}

case class Zone(
  id: Int = 0,
  publisherId: Int,
  name: String,
)

object Zone {
  implicit val format: Format[Zone] = Json.format[Zone] // Combined read/write
  // implicit val publisherReads: Reads[Publisher] = Json.reads[Publisher] // JsValue to case class obj
  // implicit val publisherWrites: Writes[Publisher] = Json.writes[Publisher] // Case class obj to JsValue
}
