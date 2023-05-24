package models.daos

import models.{Campaign, Creative, Zone}
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

  def getSelectedValuesByHash(
    hash: String
  ): Future[Option[Map[String, Any]]] = {

    val query = {
      sql"""
        select cr.id, cr.filename, a.name as 'advertiser_name', p.name as 'publisher_name' from creative cr
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
        Map("id" -> t._1, "filename" -> t._2, "advertiser_name" -> t._3, "publisher_name" -> t._4)
      )
    }
  }

  def getPoolByCampaignsAndZone(
    campaigns: Vector[Campaign],
    zone: Zone,
  ): Future[Vector[Creative]] = {
    val campaignIds = campaigns.map(c => c.id)
    val query       = {
      sql"""
        select * from creative
        where campaign_id in(#${campaignIds.mkString(",")})
        and active = true
        and width between ${zone.minWidth} and ${zone.maxWidth}
        and height between ${zone.minHeight} and ${zone.maxHeight}
      """.as[Creative]
    }
    db.run(query)
  }

}
