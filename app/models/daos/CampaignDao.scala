package models.daos

import models.Campaign
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.{GetResult, JdbcProfile}

import java.sql.Timestamp
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class CampaignDao @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  implicit val getResultCampaign: AnyRef with GetResult[Campaign] = {
    // SQL Datetime is considered a Timestamp by JDBC driver.
    GetResult(r =>
      Campaign(
        r.<<,
        r.<<,
        r.<<,
        r.<<,
        r.<<,
        r.nextTimestamp().toLocalDateTime,
        r.nextTimestamp().toLocalDateTime,
      )
    )
  }

  def getAll(): Future[Vector[Campaign]] = {
    val query = sql"select * from campaign;".as[Campaign]
    db.run(query)
  }

  def get(id: Int): Future[Option[Campaign]] = {
    val query = sql"select * from campaign where id = ${id};".as[Campaign]
    db.run(query).map(_.headOption)
  }

  def getCampaignsInFlightByPublisherId(publisherId: Int): Future[Vector[Campaign]] = {
    val query =
      sql"""
        select * from campaign
        where publisher_id = ${publisherId}
        AND active = true
        AND NOW() BETWEEN start_datetime AND end_datetime
      """.as[Campaign]
    db.run(query)

  }

  /*
   * Returns id of inserted row.
   *  */
  def add(campaign: Campaign): Future[Int] = {
    val query1           = {
      sqlu"""
        insert into campaign
           (publisher_id, advertiser_id, name, active, start_datetime, end_datetime)
        value (
          ${campaign.publisherId},
          ${campaign.advertiserId},
          ${campaign.name},
          ${campaign.active},
          ${Timestamp.valueOf(campaign.startDateTime)},
          ${Timestamp.valueOf(campaign.endDateTime)},
        );
      """
    }
    val query2           = sql"""select last_insert_id();""".as[Int]
    val eventualInsertId = db.run(query1.andThen(query2).transactionally)
    eventualInsertId.map(_.head)
  }

  /*
   * Returns num of rows deleted.
   *  */
  def delete(id: Int): Future[Int] = {
    val query = sqlu"""delete from campaign where id = ${id};"""
    db.run(query.transactionally)
  }

}
