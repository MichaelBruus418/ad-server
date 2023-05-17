package models.daos

import models.Advertiser
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.{GetResult, JdbcProfile}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class AdvertiserDao @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  implicit val getResultAdvertiser: AnyRef with GetResult[Advertiser] = {
    GetResult(r => Advertiser(r.<<, r.<<))
  }

  def getAll(): Future[Vector[Advertiser]] = {
    val query = sql"select * from advertiser;".as[Advertiser]
    db.run(query)
  }

  def get(id: Int): Future[Option[Advertiser]] = {
    val query = sql"select * from advertiser where id = ${id};".as[Advertiser]
    db.run(query).map(_.headOption)
  }

  /*
   * Returns id of inserted row.
   *  */
  def add(advertiser: Advertiser): Future[Int] = {
    val query1           =
      sqlu"""insert into advertiser (name) value (${advertiser.name});"""
    val query2           = sql"""select last_insert_id();""".as[Int]
    val eventualInsertId = db.run(query1.andThen(query2).transactionally)
    eventualInsertId.map(_.head)
  }

  /*
   * Returns num of rows deleted.
   *  */
  def delete(id: Int): Future[Int] = {
    val query = sqlu"""delete from advertiser where id = ${id};"""
    db.run(query)
  }

}
