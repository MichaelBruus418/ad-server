package models.daos

import models.{Campaign, Creative, Zone}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.{GetResult, JdbcProfile}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class CreativeDao @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  implicit val getResultCreative: AnyRef with GetResult[Creative] =
    GetResult(r => Creative(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<))

  def getAll(): Future[Vector[Creative]] = {
    val query = sql"select * from creative;".as[Creative]
    db.run(query)
  }

  def get(id: Int): Future[Option[Creative]] = {
    val query = sql"select * from creative where id = ${id};".as[Creative]
    db.run(query).map(_.headOption)
  }


  def getPoolByCampaignsAndZone(campaigns: Vector[Campaign], zone: Zone): Future[Vector[Creative]] = {
    val campaignIds = campaigns.map(c => c.id)
    val query = {
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
