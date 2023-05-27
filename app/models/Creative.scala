package models

import play.api.libs.json._

import java.time.LocalDateTime

case class Creative(
  id: Int = 0,
  campaignId: Int,
  filepath: String,
  hash: String,
  disabled: Boolean,
  width: Int,
  height: Int,
  served: Int,
  downloaded: Int,
  viewable: Int,
  impressionMetric: String,
  impressionTarget: Int,
)

object Creative {
  implicit val format: Format[Creative] =
    Json.format[Creative]
}

