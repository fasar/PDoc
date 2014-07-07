package com.openbee.test.crawler

import java.net.URL
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.client.methods.HttpGet
import scala.io.Source

/**
 * Created by fabien_s on 01/02/14.
 */
trait UrlGetter {

  def httpGet(url: URL): String

}
