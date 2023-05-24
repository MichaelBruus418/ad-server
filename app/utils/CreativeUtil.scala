package utils

import models.Creative
import models.daos.{CampaignDao, CreativeDao, PublisherDao, ZoneDao}

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Random, Success, Try}

@Singleton
class CreativeUtil @Inject() (
  val campaignDao: CampaignDao,
  val creativeDao: CreativeDao,
  val publisherDao: PublisherDao,
  val zoneDao: ZoneDao,
) {

  def getCreative(
    publisherName: String,
    zoneName: String,
  ): Future[Option[Creative]] = {

    val result = for {
        publisherOpt <- publisherDao.getByName(publisherName)
        campaigns    <- campaignDao.getCampaignsInFlightByPublisherId(publisherOpt.get.id)
        zoneOpt      <- zoneDao.getByNameAndPublisherId(zoneName, publisherOpt.get.id)
        creatives    <- creativeDao.getPoolByCampaignsAndZone(campaigns, zoneOpt.get)
      } yield {
        val creativesA = creatives.toArray
        creativesA(Random.nextInt(creativesA.length))
      }
      result.map(Option(_))
  }

  def getIdAndFilepathByHash(hash: String): Future[Option[(Int, String, String)]] = {

    for {
      result <- creativeDao.getSelectedValuesByHash(hash)
    } yield {
      result.map(m => {
        // Keys in Map:  id, filename, advertiser_name, publisher_name
        var dir = FilenameUtil.sanitize(m("publisher_name").toString).toLowerCase()
        dir += "/" + FilenameUtil.sanitize(m("advertiser_name").toString).toLowerCase()
        val filename = FilenameUtil.sanitize(m("filename").toString).toLowerCase()
        (m("id"), dir, filename).asInstanceOf[(Int, String, String)]
      })
    }
  }

}
