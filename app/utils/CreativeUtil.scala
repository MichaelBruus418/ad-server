package utils

import configs.GenericConfig
import models.Creative
import models.daos.{CreativeDao, PublisherDao, ZoneDao}

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration.Duration
import scala.util.Random
import scala.util.matching.Regex

@Singleton
class CreativeUtil @Inject() (
  val creativeDao: CreativeDao,
  val publisherDao: PublisherDao,
  val zoneDao: ZoneDao,
) {
  val basePath: String = GenericConfig.getBasePath("creatives").getOrElse("")

  def getCreative(
    publisherName: String,
    zoneName: String,
    includeHtml: Boolean = false,
  ): Future[(Option[Creative], Option[String])] = {
    for {
      creatives <- creativeDao.getPoolByPublisherNameAndZoneName(
        publisherName,
        zoneName,
      )
    } yield {
      if (creatives.isEmpty) {
        (None, None)
      } else {
        // TODO: Better business logic than simply choosing by random
        val creative = creatives(Random.nextInt(creatives.length))
        if (includeHtml) {
          val html = Await
            .result(getIdAndFilepathByHash(creative.hash), Duration.Inf)
            .map(v => {
              val (id, dir, filepath) = v
              try {
                val src        =
                  scala.io.Source.fromFile(basePath + dir + "/" + filepath)
                val htmlString = src.mkString
                src.close()
                htmlString
              } catch {
                case e: Throwable =>
                  println(e.toString)
                  "" // Empty string (not a typo).
              }
            })
          (Some(creative), html)
        } else {
          (Some(creative), None)
        }
      }
    }
  }

  /*
   * @returns  (creativeId, dir, filepath)
   *  */
  def getIdAndFilepathByHash(
    hash: String
  ): Future[Option[(Int, String, String)]] = {

    for {
      result <- creativeDao.getLinkValuesByHash(hash)
    } yield {
      result.map(m => {
        // Keys in Map:  id, filepath, advertiser_name, publisher_name
        // Dir: path to <advertiser> folder relative to creatives folder.
        // Filepath: path to html file relative to <advertiser> folder.
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

  def insertBasePathFromUrl(
    html: Option[String],
    url: String,
  ): Option[String] = {
    html.map(value => {
      val basePath = s"\n\t<base href=\"${getBasePathFromUrl(url)}\">"
      val regex    = """^(?i)((.|\n|\r)*?<head>)((.|\n|\r)*?</head>)""".r
      regex.replaceAllIn(value, m => m.group(1) + basePath + m.group(3))
    })
  }

  private def getBasePathFromUrl(url: String): String = {
    val regex = "\\w+\\.html$".r
    regex.replaceAllIn(url, "")
  }

}
