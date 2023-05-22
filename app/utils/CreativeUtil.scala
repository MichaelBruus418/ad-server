package utils

import models.daos.{CreativeDao, PublisherDao}

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class CreativeUtil @Inject() (
  val creativeDao: CreativeDao,
  val publisherDao: PublisherDao,
) {

  def getLink(publisherName: String, zoneName: String): Future[String] = {
    val tmp = creativeDao.getAll()
    val tmp2 = publisherDao.getAll()

    Future("bla bla bla")
  }

}
