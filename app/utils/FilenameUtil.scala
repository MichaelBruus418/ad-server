package utils

object FilenameUtil {

  /*
  * Very simple sanatizing of filename.
  * Any non-legal char is replaced by underscore.
  *  */
  def sanitize(src: String, encodeSpace: Boolean = true, rewriteDK: Boolean = true): String = {
    var illegal = "/:*?<>|\\"
    if (encodeSpace) illegal += " "
    val regex = "[" + illegal + "]".r
    var dst = src.replaceAll(regex, "_")
    if (rewriteDK) {
      dst = dst.replaceAll("æ", "ae")
      dst = dst.replaceAll("ø", "oe")
      dst = dst.replaceAll("å", "aa")
      dst = dst.replaceAll("Æ", "AE")
      dst = dst.replaceAll("Ø", "OE")
      dst = dst.replaceAll("Å", "AA")
      dst
    }
    else {
      dst
    }
  }
}
