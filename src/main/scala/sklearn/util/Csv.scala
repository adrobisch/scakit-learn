package sklearn.util

import java.io.{FileInputStream, InputStream}

object Csv {
  private val defaultSeparator = ","
  private val classPathPrefix = "classpath:"

  def loadFile[T](fileName: String,
                  extract: Array[String] => T,
                  separator: Option[String] = None): Iterator[T] = {
    val input = if (fileName.startsWith(classPathPrefix)) {
      getClass.getClassLoader.getResourceAsStream(fileName.replaceFirst(classPathPrefix, ""))
    } else new FileInputStream(fileName)

    loadCsv[T](input)(extract, separator)
  }

  def loadCsv[T](input: InputStream)(
                 extract: Array[String] => T,
                 separator: Option[String] = None): Iterator[T] = {
    scala.io.Source
      .fromInputStream(input)
      .getLines()
      .map(line => extract(line.split(separator.getOrElse(defaultSeparator))))
  }

}
