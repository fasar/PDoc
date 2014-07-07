package com.openbee.test.bayesian

/**
 * Created by fabien_s on 23/01/14.
 */
trait Classifier {

  def wordProbalility(word:Word, category: Category): Probability

  def categorieProbablilityKnowingDocument(document:Document, category: Category): Probability

  def classify(document:Document): Map[Category, Probability] = {
    // val res = Map[Category, Probability]
    ???
  }
}
