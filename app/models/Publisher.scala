package models

import play.api.libs.json.{Format, Json, Reads, Writes}

case class Publisher(
  id: Int = 0,
  name: String,
)

object Publisher {
  implicit val format: Format[Publisher] = Json.format[Publisher] // Combined read/write
  // implicit val publisherReads: Reads[Publisher] = Json.reads[Publisher] // JsValue to case class obj
  // implicit val publisherWrites: Writes[Publisher] = Json.writes[Publisher] // Case class obj to JsValue
}
