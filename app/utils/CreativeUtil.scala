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
  val zoneDao: ZoneDao
) {

  def getCreative(publisherName: String, zoneName: String): Future[Creative] = {

    val eventualCreative = for {
      eventualPublisher <- publisherDao.getByName(publisherName)
      campaigns <- campaignDao.getCampaignsInFlightByPublisherId(eventualPublisher.get.id)
      zone <- zoneDao.getByNameAndPublisherId(zoneName, eventualPublisher.get.id)
      creatives <- creativeDao.getPoolByCampaignsAndZone(campaigns, zone.get)
    } yield {
      val creativesA = creatives.toArray
      val creative = creativesA(Random.nextInt(creativesA.length))
      println("Pool size: " + creativesA.length)
      println(creative)
      creative
    }

    eventualCreative

  }

}
