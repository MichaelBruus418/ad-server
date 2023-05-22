package models.daos

import models.Creative
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.{GetResult, JdbcProfile}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class CreativeDao @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  implicit val getResultCreative: AnyRef with GetResult[Creative] =
    GetResult(r => Creative(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<))

  def getAll(): Future[Vector[Creative]] = {
    val query = sql"select * from creative;".as[Creative]
    db.run(query)
  }

  def get(id: Int): Future[Option[Creative]] = {
    val query = sql"select * from creative where id = ${id};".as[Creative]
    db.run(query).map(_.headOption)
  }



}
