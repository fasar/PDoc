package com.openbee.test.crawler

import java.net.URL
import java.io._
import scala.collection.mutable
import java.util.{Calendar, UUID, Properties}
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.io.Source

/**
 * Created by fabien_s on 01/02/14.
 */
class UrlGetterCachedProperties(
                        cacheFolder: File,
                        urlGetter: UrlGetter
                       ) extends UrlGetter {

  // Runtime Cache
  private val cache: mutable.HashMap[String, String] = mutable.HashMap()

  // File Cache
  private val docMapFile = new File(cacheFolder, "fileMap.properties")
  private val docMap = new Properties()

  private val nbElemToStore = 10

  // Constructeur
  loadCache()

  def  httpGet(url: URL): String = {

    val page =
      if( ! isCached(url)) {
        val pageStr = urlGetter.httpGet(url)
        cache.put(url.toString, pageStr)
        if(cache.size % nbElemToStore == 0 ) {
          saveCache()
        }
        pageStr
      } else {
        println("Load page from cache : " + url.toString)
        cache.get(url.toString).get
      }
    page
  }

  def isCached(url: URL): Boolean = {
    cache.contains(url.toString)
  }

  def saveCache() {
    // Save each html file an put a link to each generated file in docMap
    for((url, doc) <- cache) {
      val file = docMap.getProperty(url)
      if( file == null) {
        val uuid = UUID.randomUUID().toString().replaceAll("-", "");
        val curUrl = new URL(url)
        val fileName = uuid + ".html"
        val curFile = new File(cacheFolder, fileName)
        try {
          val writer = new PrintWriter(new BufferedWriter(new FileWriter(curFile)));
          writer.print(doc)
          writer.close()
          docMap.put(url, curFile.getName)
          println("Save file for [" + url + "] in : " + curFile.getName)
        }catch {
          case i: IOException => i.printStackTrace();
        }
      }
    }
    // Backup and raz map url -> doc file
    try {
      if(docMapFile.exists()) {
        backup(docMapFile)
        docMapFile.delete()
      }
      docMapFile.createNewFile()
    } catch {
      case e: Throwable => e.printStackTrace()
    }

    try {
      val out = new FileOutputStream(docMapFile)
      docMap.store(out, "date : ")
      out.close
      println("Save map url -> doc file in : " + docMapFile.getCanonicalPath)
    } catch {
      case e: Throwable => e.printStackTrace()
    }
  }

  private def backup(file: File) {
    val fileName = file.getCanonicalPath
    var i = 1
    var fileBackup = new File(fileName + "OLD" + i)
    while(fileBackup.exists()) {
      i = i+1
      fileBackup = new File(fileName + "OLD" + i)
    }
    copy2files(file, fileBackup)
  }

  private def copy2files(source: File, dest:File) {
    var input: InputStream = null
    var output: OutputStream = null
    try {
      input = new FileInputStream(source)
      output = new FileOutputStream(dest)
      val buf: Array[Byte] = new Array[Byte](1024)
      var bytesRead = input.read(buf)
      while ( bytesRead> 0) {
        output.write(buf, 0, bytesRead)
        bytesRead = input.read(buf)
      }
    } catch {
      case e: Throwable => e.printStackTrace()
    } finally {
      if(input != null)
        input.close()
      if(output != null)
        output.close()
    }
  }

  private def loadCache() {
    try {
      if( docMapFile.exists()) {
        val in = new FileInputStream(docMapFile)
        docMap.load(in)
        in.close()
        val map: mutable.Map[String, String] = docMap
        for( (url, doc) <- map ) {
          val file = new File(cacheFolder, doc)
          val in = Source.fromFile(file)
          cache.put(url, in.mkString)
          in.close()
        }
      }
    } catch {
      case i: IOException => i.printStackTrace()
    }
  }

  def clearCache() {
    for(elem <- cacheFolder.listFiles() if(elem.isFile)) {
      elem.delete()
    }
    cache.clear()
  }
}


