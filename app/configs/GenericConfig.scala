package configs

object GenericConfig {

  /*
  * @returns BasePath relative to project dir.
  *  */
  def getBasePath(key: String): Option[String] = {
    val paths = Map(
      "creatives" -> "creatives/",
    )

    val index = searchIndexCaseInsensitive(paths, key)
    if (index != -1) {
      Some(paths.values.toIndexedSeq(index))
    } else {
      None
    }
  }

  /*
  * Hard-coded credentials for the time being.
  * ...and no, passwords shouldn't be stored in plain-text,
  * but this is only a proof-of-concept Ad-Server, so chill!
  *  */
  def getPasswordByUserName(userName: String): Option[String] = {
    val credentials = Map(
      "Jyllands-Posten" -> "1234",
      "Finans" -> "5678"
    )

    val index = searchIndexCaseInsensitive(credentials, userName)
    if (index != -1) {
      Some(credentials.values.toIndexedSeq(index))
    } else {
      None
    }

  }

  /*
  * @returns  index or -1 if key is not found.
  *  */
  private def searchIndexCaseInsensitive(src: Map[String, Any], key: String): Int = {
    // Hack to ignore case in map keys
    src.keys
      .map(_.toLowerCase())
      .toIndexedSeq
      .indexOf(key.toLowerCase())
  }

}
