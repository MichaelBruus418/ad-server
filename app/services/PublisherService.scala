package services

import models.Publisher
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


class PublisherService @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._
  def test(): String = {
    "This String is from PublisherService"
  }

  def getAll(): Unit = {
    val query = sql"select * from publisher;".as[String]
    val queryResult: Future[Vector[String]] = db.run(query)
    queryResult.map(println(_))

  }

}
