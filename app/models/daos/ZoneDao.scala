package models.daos

import models.Zone
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.{GetResult, JdbcProfile}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ZoneDao @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  implicit val getResultZone: AnyRef with GetResult[Zone] =
    GetResult(r => Zone(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<))

  def getAll(): Future[Vector[Zone]] = {
    val query = sql"select * from zone;".as[Zone]
    db.run(query)
  }

  def get(id: Int): Future[Option[Zone]] = {
    val query = sql"select * from zone where id = ${id};".as[Zone]
    db.run(query).map(_.headOption)
  }

  def getByPublisherId(publisherId: Int): Future[Vector[Zone]] = {
    val query =
      sql"select * from zone where publisher_id = ${publisherId};".as[Zone]
    db.run(query)
  }

}
