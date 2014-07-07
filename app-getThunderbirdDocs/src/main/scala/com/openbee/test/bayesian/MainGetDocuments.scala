package com.openbee.test.bayesian

import java.io.{FileWriter, File}
import com.openbee.test.crawler.{UrlGetterCachedProperties, UrlNetGetter, UrlSourceThunderbird}
import org.jsoup.Jsoup

/**
 * Created by fabien_s on 31/01/14.
 */
object MainGetDocuments {
  val feedPath = Configuration.feedPath
  val categories = Configuration.categories
  val recordFolder = Configuration.recordFolder

  val urlGetter = new UrlGetterCachedProperties(Configuration.cacheFolder, new UrlNetGetter())


  def main(args: Array[String]) {
    for(curCategory <- categories) {
      println("I'm doing category : " + curCategory)
      val UrlSource = new UrlSourceThunderbird(feedPath, curCategory)
      val urls = UrlSource.getUrls()
      for( (url, numUrl) <- urls.zipWithIndex) {
        if(!urlGetter.isCached(url)) {
          println(s" [$numUrl] I'm getting url : $url ($curCategory)")
          val html: String = urlGetter.httpGet(url)
          val res = getLeMondeDocument(html)
          saveRecord(res, curCategory)
          Thread.sleep(300)
        } else {
          println(s" [$numUrl] url already done: $url ($curCategory)")
        }
      }
    }

    urlGetter.saveCache()
  }


  def getLeMondeDocument(html: String): String = {
    val doc = Jsoup.parse(html)
    val htmlArticle = doc.select(".article")
    val title = htmlArticle.select("h1.tt2").text()
    val docBody = htmlArticle.select("div#articleBody").text()
    val res = title + "\n" + "\n" + docBody

    res
  }


  def saveRecord(document: String, curCategory: String) {
    val curFolder = new File(recordFolder, curCategory)
    curFolder.mkdirs()
    val filename = sanitize(document.take(80))
    val recordFile = new File(curFolder, filename + ".txt")
    recordFile.createNewFile()
    val out = new FileWriter(recordFile)
    out.write(document)
    out.close()
  }

  def sanitize(filename:String) = {
    filename.replaceAll("""[^0-9A-Za-z.() {}~@]""", "_")
  }

}
