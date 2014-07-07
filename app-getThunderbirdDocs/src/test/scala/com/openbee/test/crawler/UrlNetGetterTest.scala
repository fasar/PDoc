package com.openbee.test.crawler

import org.junit.{Assert, Test}
import java.net.URL

/**
 * Created by fabien_s on 02/02/14.
 */
class UrlNetGetterTest {
  val urlstr = "http://localhost:9000/"

  @Test
  def testGetUrl() {
    val httpGetter = new UrlNetGetter()

    val url = new URL(urlstr)
    val res = httpGetter.httpGet(url)
    val str = res
    println("res: " + str)
    Assert.assertTrue(str.size > 0)
  }

}
