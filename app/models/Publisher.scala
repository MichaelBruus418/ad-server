package models

import play.api.libs.json.{Format, Json, Reads, Writes}

case class Publisher(
  id: Int = 0,
  name: String,
)

object Publisher {
  implicit val format: Format[Publisher] = Json.format[Publisher]
}
