package utils

import models.Creative
import models.daos.{CreativeDao, PublisherDao, ZoneDao}

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Random

@Singleton
class CreativeUtil @Inject() (
  val creativeDao: CreativeDao,
  val publisherDao: PublisherDao,
  val zoneDao: ZoneDao,
) {

  def getCreative(
    publisherName: String,
    zoneName: String,
  ): Future[Option[Creative]] = {
    for {
      creatives <- creativeDao.getPoolByPublisherNameAndZoneName(
        publisherName,
        zoneName,
      )
    } yield {
      if (creatives.isEmpty) {
        None
      } else {
        // TODO: Better business logic than simply choosing by random
        Some(creatives(Random.nextInt(creatives.length)))
      }
    }
  }

  def getIdAndFilepathByHash(
    hash: String
  ): Future[Option[(Int, String, String)]] = {

    for {
      result <- creativeDao.getLinkValuesByHash(hash)
    } yield {
      result.map(m => {
        // Keys in Map:  id, filepath, advertiser_name, publisher_name
        // Filepath is relative to advertiser folder.
        var dir      =
          FilenameUtil.sanitize(m("publisher_name").toString).toLowerCase()
        dir += "/" + FilenameUtil
          .sanitize(m("advertiser_name").toString)
          .toLowerCase()
        val filepath =
          FilenameUtil.sanitize(m("filepath").toString).toLowerCase()
        (m("id"), dir, filepath).asInstanceOf[(Int, String, String)]
      })
    }
  }

}
