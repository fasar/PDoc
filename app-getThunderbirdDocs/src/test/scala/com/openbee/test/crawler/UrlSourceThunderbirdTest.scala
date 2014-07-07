package com.openbee.test.crawler

import org.junit.Test
import org.junit.Assert
import com.openbee.test.bayesian.BayesianClassifier
import java.io.File

/**
 * Created by fabien_s on 24/01/14.
 */
class UrlSourceThunderbirdTest {

  val feedPath = new File("""C:\Users\fabien_s\AppData\Roaming\Thunderbird\Profiles\ex1nbcas.default\Mail\Feeds""")

  val urlSource = new UrlSourceThunderbird(
                        feedPath,
                        "Économie 1bc8f1c6")

  @Test def getUrls {
    val urls = urlSource.getUrls()
    val url = urls.next()
    Assert.assertTrue("Start string is http://www.lemonde.fr/", url.toString.startsWith("http://www.lemonde.fr/"))
    Assert.assertTrue("getUrls is not empty", urls.size > 0)
  }

}
