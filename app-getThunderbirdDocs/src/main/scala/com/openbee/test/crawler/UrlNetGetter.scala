package com.openbee.test.crawler

import java.net.URL
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.client.methods.HttpGet
import scala.io.Source

/**
 * Created by fabien_s on 02/02/14.
 */
class UrlNetGetter extends UrlGetter {

  def httpGet(url: URL): String = {
    val client: HttpClient = HttpClientBuilder.create().build();
    val request: HttpGet = new HttpGet(url.toString);

    // add request header
    request.addHeader("User-Agent", "Machine Learning Test");
    val response = client.execute(request);

    System.out.println("Response Code : "
      + response.getStatusLine().getStatusCode());

    val content = response.getEntity().getContent()
    val input = Source.fromInputStream(content)
    val lines = input.getLines()

    lines.mkString("\n")
  }

}
