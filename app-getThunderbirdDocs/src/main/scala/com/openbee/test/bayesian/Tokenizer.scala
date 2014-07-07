package com.openbee.test.bayesian

/**
 * Created by fabien_s on 22/01/14.
 */
trait Tokenizer {

  def tokenize(doc: String): Document

}
