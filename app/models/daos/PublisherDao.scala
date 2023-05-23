package models.daos

import models.Publisher
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.{GetResult, JdbcProfile}
import slick.jdbc.MySQLProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class PublisherDao @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  implicit val getResultPublisher: AnyRef with GetResult[Publisher] =
    GetResult(r => Publisher(r.<<, r.<<))

  def getAll(): Future[Vector[Publisher]] = {
    val query = sql"select * from publisher;".as[Publisher]
    db.run(query)
  }

  def get(id: Int): Future[Option[Publisher]] = {
    val query = sql"select * from publisher where id = ${id};".as[Publisher]
    db.run(query).map(_.headOption)
  }

  def getByName(name: String): Future[Option[Publisher]] = {
    val query = sql"select * from publisher where name = ${name};".as[Publisher]
    db.run(query).map(_.headOption)
  }

  /*
   * Returns id of inserted row.
   *  */
  def add(publisher: Publisher): Future[Int] = {
    val query1           =
      sqlu"""insert into publisher (name) value (${publisher.name});"""
    val query2           = sql"""select last_insert_id();""".as[Int]
    val eventualInsertId = db.run(query1.andThen(query2).transactionally)
    eventualInsertId.map(_.head)
  }

  /*
   * Returns num of rows deleted
   *  */
  def delete(id: Int): Future[Int] = {
    val query = sqlu"""delete from publisher where id = ${id};"""
    db.run(query.transactionally)
  }

}
