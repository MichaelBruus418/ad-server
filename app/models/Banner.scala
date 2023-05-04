package models

import play.api.libs.json._

case class Banner(
  id: Int = 1,
  publisher: String = "jypo",
)

object Banner {
  implicit val format: Format[Banner] = Json.format[Banner] // Combined read/write
  // implicit val bannerReads: Reads[Banner] = Json.reads[Banner] // JsValue to case class obj
  // implicit val bannerWrites: Writes[Banner] = Json.writes[Banner] // Case class obj to JsValue

  // --- OWrites / OFormat --
  // I'm not sure what the difference between Writes and OWrites is.
  // implicit val bannerWrites: OWrites[Banner] = Json.writes[Banner] // Case class obj to JsValue
  // implicit val oFormat: OFormat[Banner] = Json.format[Banner] // Combined read/wtite

}



