package com.openbee.test.bayesian

import java.util.StringTokenizer

/**
 * Created by fabien_s on 22/01/14.
 */
class SimpleTokenizer extends Tokenizer {

  def tokenize(doc: String): Document = {
    val tokenizer = new StringTokenizer(doc, " ,.()\"' «»-\r\t\n", false)
    new Iterator[String] {
      def hasNext = tokenizer.hasMoreElements
      def next = tokenizer.nextElement().asInstanceOf[String].toLowerCase
    }.filter( string => string.size > 4 )
  }

}
