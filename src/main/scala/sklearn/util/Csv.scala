package sklearn.util

object Csv {
  private val defaultSeparator = ","

  def loadCsv[T](fileName: String,
                 extract: Array[String] => T,
                 separator: Option[String] = Some(defaultSeparator)): Iterator[T] =
    scala.io.Source
      .fromInputStream(getClass.getClassLoader.getResourceAsStream(fileName))
      .getLines()
      .map(line => extract(line.split(separator.getOrElse(defaultSeparator))))
}
