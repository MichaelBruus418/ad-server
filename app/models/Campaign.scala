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
  // implicit val bannerReads: Reads[Banner] = Json.reads[Banner] // JsValue to case class obj
  // implicit val bannerWrites: Writes[Banner] = Json.writes[Banner] // Case class obj to JsValue
}


/*
  val startDateTime    = LocalDateTime.now()
  val endDateTime      = LocalDateTime.parse("2023-07-29T12:00:01")
  val banner           = Banner(
    zoneId = 1,
    advertiserId = 1,
    name = "foo",
    startDateTime = startDateTime,
    endDateTime = endDateTime,
    targetNumOfViews = 999,
 )

  val eventualInsertId = bannerDao.add(banner)
  eventualInsertId.onComplete {
    case Success(v) => println("Insert id: " + v)
    case Failure(e) => println(e)
 }
  */


