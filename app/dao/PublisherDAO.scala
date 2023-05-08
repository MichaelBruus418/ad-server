package dao

import models.Publisher
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.{GetResult, JdbcProfile}
import slick.jdbc.MySQLProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

// See:
// https://scala-slick.org/doc/2.1.0/sql.html
// https://blog.knoldus.com/sql-made-easy-and-secure-with-slick/

class PublisherDAO @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  implicit val getResultPublisher: AnyRef with GetResult[Publisher] =
    GetResult(r => Publisher(r.<<, r.<<))

  def getAll(): Future[Vector[Publisher]] = {
    val query = sql"select * from publisher;".as[Publisher]
    db.run(query.transactionally)
    // queryResult.onComplete(println(_))
  }

  def add(publisher: Publisher): Unit = {
    println("ADD method")
    val query = sqlu"""insert into publisher (name) values (${publisher.name});"""
    db.run(query)
  }

}


