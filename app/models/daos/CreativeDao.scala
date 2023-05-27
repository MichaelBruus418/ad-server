package models.daos

import models.Creative
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.{GetResult, JdbcProfile}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class CreativeDao @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  implicit val getResultCreative: AnyRef with GetResult[Creative] =
    GetResult(r =>
      Creative(
        r.<<,
        r.<<,
        r.<<,
        r.<<,
        r.<<,
        r.<<,
        r.<<,
        r.<<,
        r.<<,
        r.<<,
        r.<<,
        r.<<,
      )
    )

  def getAll(): Future[Vector[Creative]] = {
    val query = sql"select * from creative;".as[Creative]
    db.run(query)
  }

  def get(id: Int): Future[Option[Creative]] = {
    val query = sql"select * from creative where id = ${id};".as[Creative]
    db.run(query).map(_.headOption)
  }

  /*
  * Returns values necessary for serving creative.
  *  */
  def getLinkValuesByHash(
    hash: String
  ): Future[Option[Map[String, Any]]] = {
    val query = {
      sql"""
        select cr.id, cr.filepath, a.name as 'advertiser_name', p.name as 'publisher_name' from creative cr
        inner join campaign ca
        on ca.id = cr.campaign_id
        inner join advertiser a
        on a.id = ca.advertiser_id
        inner join publisher p
        on p.id = ca.publisher_id
        where cr.hash = '#${hash}'
      """.as[(Int, String, String, String)]
    }

    for {
      vector <- db.run(query)
    } yield {
      vector.headOption.map(t =>
        Map("id" -> t._1, "filepath" -> t._2, "advertiser_name" -> t._3, "publisher_name" -> t._4)
      )
    }
  }

  /*
  * Returns in-flight Creatives relevant for publisher and zone.
  *  */
  def getPoolByPublisherNameAndZoneName(publisherName: String, zoneName: String): Future[Vector[Creative]] = {
    val query = {
      sql"""
      select cr.* from creative cr
      inner join campaign ca
      on ca.id = cr.campaign_id
      and ca.disabled = 0
      and cr.disabled = 0
      and now() between ca.start_datetime and ca.end_datetime
      and (
        cr.impressionTarget = 0
        or (
          (cr.impressionMetric = 'served' and cr.served < cr.impressionTarget)
          or (cr.impressionMetric = 'downloaded' and cr.downloaded < cr.impressionTarget)
          or (cr.impressionMetric = 'viewable' and cr.viewable < cr.impressionTarget)
        )
      )
      inner join publisher p
      on p.id = ca.publisher_id
      and p.name = ${publisherName}
      inner join zone z
      on z.publisher_id = p.id
      and z.name = ${zoneName}
      where cr.width between z.minWidth and z.maxWidth
      and cr.height between z.minHeight and z.maxHeight
      """.as[Creative]
    }

    db.run(query)
  }

}
