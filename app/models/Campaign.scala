package models

import play.api.libs.json._

import java.time.LocalDateTime

case class Campaign(
  id: Int = 0,
  publisherId: Int,
  advertiserId: Int,
  name: String,
  disabled: Boolean,
  startDateTime: LocalDateTime,
  endDateTime: LocalDateTime,
) {
  if (startDateTime.compareTo(endDateTime) > 0) {
    throw new IllegalArgumentException(
      "startDateTime can not be earlier than endDateTime."
    )
  }
}

object Campaign {
  implicit val format: Format[Campaign] =
    Json.format[Campaign] // Combined read/write
}




