package utils

object FilenameUtil {

  /*
  * Very simple sanatizing of filename.
  * Any not-allowed char is replaced by underscore.
  *  */
  def sanitize(src: String, encodeSpace: Boolean = true): String = {
    var allowed = "^a-zA-Z0-9._-"
    if (encodeSpace) allowed += " "
    val regex = "[^" + allowed + "]".r
    src.replaceAll(regex, "_")
  }

}
