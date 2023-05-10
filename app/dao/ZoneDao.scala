package dao

import models.Zone
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.{GetResult, JdbcProfile}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ZoneDao @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  implicit val getResultZone: AnyRef with GetResult[Zone] =
    GetResult(r => Zone(r.<<, r.<<, r.<<))

  def getAll(): Future[Vector[Zone]] = {
    val query = sql"select * from zone;".as[Zone]
    db.run(query.transactionally)
  }

  /*
   * Returns id of inserted row.
   *  */
  def add(zone: Zone): Future[Int] = {
   /*  val query1 =
      sqlu"""insert into publisher (name) values (${publisher.name});"""
    val query2 = sql"""select last_insert_id();""".as[Int]
    val result = db.run(query1.andThen(query2).transactionally)
    result.map(_.head) */

    ???
  }

  /*
  * Returns num of rows deleted
  *  */
  def delete(id: Int): Future[Int] = {
   /*  println("Delete id: " + id)
    val query = sqlu"""delete from publisher where id = ${id};"""
    db.run(query.transactionally) */

    ???
  }

}
