package models

import play.api.libs.json._

import java.time.LocalDateTime

case class Creative(
  id: Int = 0,
  campaignId: Int,
  filname: String,
  hash: String,
  active: Boolean,
  width: Int,
  height: Int,
  served: Int,
  downloaded: Int,
  viewable: Int,
  targetMetric: String,
  targetValue: Int,
)

object Creative {
  implicit val format: Format[Creative] =
    Json.format[Creative] // Combined read/write
  // implicit val bannerReads: Reads[Banner] = Json.reads[Banner] // JsValue to case class obj
  // implicit val bannerWrites: Writes[Banner] = Json.writes[Banner] // Case class obj to JsValue
}

