package com.openbee.test.crawler

import java.net.URL

/**
 * Created by fabien_s on 01/02/14.
 */
trait UrlSource {
  def getUrls(): Iterator[URL]
}
