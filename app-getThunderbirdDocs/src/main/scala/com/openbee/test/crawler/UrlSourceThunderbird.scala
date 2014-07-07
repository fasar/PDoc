package com.openbee.test.crawler

import java.net.URL
import java.io.File
import scala.io.Source

/**
 * Created by fabien_s on 01/02/14.
 */
class UrlSourceThunderbird (
                      val feedsPath: File,
                      val categorie: String
                            ) extends UrlSource {

  def getUrls(): Iterator[URL] = {
    val file = new File(feedsPath, categorie)
    val lines = Source.fromFile(file, "UTF-8").getLines()

    val res =
      for( line <- lines
           if line.startsWith("Content-Base: http://www.lemonde.fr")
      ) yield {
        println(line.drop(14))
        new URL(line.drop(14))
      }
    res
  }


}
