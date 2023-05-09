package dao

import models.Publisher
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.{GetResult, JdbcProfile}
import slick.jdbc.MySQLProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class PublisherDao @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  implicit val getResultPublisher: AnyRef with GetResult[Publisher] =
    GetResult(r => Publisher(r.<<, r.<<))

  def getAll(): Future[Vector[Publisher]] = {
    val query = sql"select * from publisher;".as[Publisher]
    db.run(query.transactionally)
  }

  /*
   * Returns id of inserted row.
   *  */
  def add(publisher: Publisher): Future[Int] = {
    val query1 =
      sqlu"""insert into publisher (name) values (${publisher.name});"""
    val query2 = sql"""select last_insert_id();""".as[Int]
    val result = db.run(query1.andThen(query2).transactionally)
    result.map(_.head)
  }

  /*
  * Returns num of rows deleted
  *  */
  def delete(id: Int): Future[Int] = {
    println("Delete id: " + id)
    val query = sqlu"""delete from publisher where id = ${id};"""
    db.run(query.transactionally)
  }

}
