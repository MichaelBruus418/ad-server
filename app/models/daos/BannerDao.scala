package models.daos

import models.Banner
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.{GetResult, JdbcProfile}
import slick.jdbc.MySQLProfile.api._

import java.sql.Timestamp
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class BannerDao @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  implicit val getResultBanner: AnyRef with GetResult[Banner] = {
    // SQL Datetime is considered a Timestamp by JDBC driver.
    GetResult(r =>
      Banner(
        r.<<,
        r.<<,
        r.<<,
        r.<<,
        r.nextTimestamp().toLocalDateTime,
        r.nextTimestamp().toLocalDateTime,
        r.<<,
        r.<<,
        r.<<,
      )
    )
  }

  def getAll(): Future[Vector[Banner]] = {
    val query = sql"select * from banner;".as[Banner]
    db.run(query)
  }

  def get(id: Int): Future[Option[Banner]] = {
    val query = sql"select * from banner where id = ${id};".as[Banner]
    db.run(query).map(_.headOption)
  }

  /*
   * Returns id of inserted row.
   *  */
  def add(banner: Banner): Future[Int] = {
    val query1           = {
      sqlu"""
        insert into banner
          (zone_id, advertiser_id, name, start_datetime, end_datetime, target_num_of_views)
        value (
          ${banner.zoneId},
          ${banner.advertiserId},
          ${banner.name},
          ${Timestamp.valueOf(banner.startDateTime)},
          ${Timestamp.valueOf(banner.endDateTime)},
          ${banner.targetNumOfViews}
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
    val query = sqlu"""delete from banner where id = ${id};"""
    db.run(query.transactionally)
  }

}
