//package com.openbee.test.crawler
//
//import org.junit.{Before, Test, Assert}
//import java.io.File
//import java.net.URL
//
///**
// * Created by fabien_s on 02/02/14.
// */
//class UrlGetterCachedTest {
//  val url_source = "http://yopyop"
//
//  class SimpleUrlGetter extends UrlGetter {
//    def httpGet(url: URL): String = List("elem1","elem2").mkString("\n")
//  }
//
//  val data = new File("data-test")
//  if(! data.exists()) {
//    data.mkdirs()
//  }
//
//  @Before
//  def before {
//    val simpleGetter = new SimpleUrlGetter
//    val httpGetter = new UrlGetterCached(data, simpleGetter)
//    httpGetter.clearCache()
//  }
//
//
//  @Test
//  def testGetUrlWithMapCache() {
//    val simpleGetter = new SimpleUrlGetter
//    val httpGetter = new UrlGetterCached(data, simpleGetter)
//    httpGetter.clearCache()
//
//    val url = new URL(url_source)
//    Assert.assertFalse(httpGetter.isCached(url))
//    val res = httpGetter.httpGet(url)
//    Assert.assertTrue(httpGetter.isCached(url))
//    Assert.assertEquals(httpGetter.httpGet(url), simpleGetter.httpGet(url))
//  }
//
//
//  @Test
//  def testGetUrlWithFileCache() {
//    val simpleGetter = new SimpleUrlGetter
//    val httpGetter = new UrlGetterCached(data, simpleGetter)
//    httpGetter.clearCache()
//
//    val url = new URL(url_source)
//    val res = httpGetter.httpGet(url)
//    httpGetter.saveCache()
//    Assert.assertTrue(httpGetter.isCached(url))
//    Assert.assertEquals(httpGetter.httpGet(url), simpleGetter.httpGet(url))
//
//    val newHttpGetter = new UrlGetterCached(data, simpleGetter)
//    Assert.assertTrue(newHttpGetter.isCached(url))
//    Assert.assertEquals(newHttpGetter.httpGet(url), simpleGetter.httpGet(url))
//  }
//
//
//
//
//}
