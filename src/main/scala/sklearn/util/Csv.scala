package sklearn.util

object Csv {
  private val defaultSeparator = ","

  def loadCsv[T](fileName: String,
                 extract: List[String] => T,
                 separator: Option[String] = Some(defaultSeparator)): Seq[T] = {
    scala.io.Source
      .fromInputStream(getClass.getClassLoader.getResourceAsStream(fileName))
      .getLines()
      .map(_.split(separator.getOrElse(defaultSeparator)).toList)
      .map(extract)
  }.toSeq
}
