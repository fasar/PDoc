//package com.openbee.test.crawler
//
//import java.net.URL
//import java.io._
//import scala.collection.mutable
//import scala.pickling._
//import scala.pickling.json._
//import scala.io.Source
//import scala.pickling.io.TextFileOutput
//
///**
// * Created by fabien_s on 01/02/14.
// */
//class UrlGetterCached(
//                        cacheFolder: File,
//                        urlGetter: UrlGetter
//                       ) extends UrlGetter {
//
//  // Runtime Cache
//  private val cache: mutable.HashMap[String, String] = mutable.HashMap()
//
//  private val cacheFile = new File(cacheFolder, "urlGetterCache.json")
//  private val nbElemToStore = 10
//
//  // Constructeur
//  loadCache()
//
//  def  httpGet(url: URL): String = {
//
//    val page =
//      if( ! isCached(url)) {
//        val pageStr = urlGetter.httpGet(url)
//        cache.put(url.toString, pageStr)
//        if(cache.size % nbElemToStore == 0 ) {
//          saveCache()
//        }
//        pageStr
//      } else {
//        cache.get(url.toString).get
//      }
//    page
//  }
//
//  def isCached(url: URL): Boolean = {
//    cache.contains(url.toString)
//  }
//
//  def saveCache() {
//    try {
//      if(cacheFile.exists()) {
//        cacheFile.delete()
//      }
//      cacheFile.createNewFile()
//      // Pickle data
//      val mapList = cache.toList
//      val dump = mapList.pickle
//      // Save to file
//      val ficOut = new TextFileOutput(cacheFile)
//      dump.pickleTo(ficOut)
//      ficOut.close()
//
//      System.out.printf("Serialized data is saved in " + cacheFile.getCanonicalPath);
//    }catch {
//      case i: IOException => i.printStackTrace();
//    }
//  }
//
//  private def loadCache() {
//    try {
//      if( cacheFile.exists()) {
//        val reader = Source.fromFile(cacheFile)
//        val dump = reader.getLines().mkString("\n")
//        val mapList = dump.unpickle[List[(String, List[String])]]
//        cache ++ mapList.toMap
//        reader.close()
//      }
//    } catch {
//      case i: IOException => i.printStackTrace()
//      case e: PicklingException => cacheFile.delete()
//      case e: Exception => e.printStackTrace()
//      case e: Throwable => e.printStackTrace()
//    }
//  }
//
//  def clearCache() {
//    if(cacheFile.exists()) {
//      cacheFile.delete()
//    }
//    cache.clear()
//  }
//}
//
//
