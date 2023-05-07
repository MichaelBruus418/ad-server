package services

import models.Publisher
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.{GetResult, JdbcProfile}
import slick.jdbc.MySQLProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


class PublisherService @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  def getAll(): Unit = {
    implicit val getResultPublisher: AnyRef with GetResult[Publisher] =
      GetResult(r => Publisher(r.<<, r.<<))
    val query = sql"select * from publisher".as[Publisher]
    val queryResult: Future[Vector[Publisher]] = db.run(query.transactionally)
    queryResult.onComplete(println(_))
  }

}


