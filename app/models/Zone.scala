package models

import play.api.libs.json.{Format, Json}

case class Zone(
  id: Int = 0,
  publisherId: Int,
  name: String,
  minWidth: Int,
  minHeight: Int,
  maxWidth: Int,
  maxHeight: Int,
)

object Zone {
  implicit val format: Format[Zone] = Json.format[Zone]
 }
