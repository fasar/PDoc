package com.openbee.test.bayesian

import org.junit.{Test, Before}
import org.junit.Assert

/**
 * Created by fabien_s on 24/01/14.
 */
class BayesianClassifierTest {

  val classifier: BayesianClassifier = {
    val counter = Counter.empty
    val document1 = "mot1 mot2".split(" ")
    val document2 = "mot1 mot3".split(" ")
    counter.train(document1.toIterator, "cat1")
    counter.train(document2.toIterator, "cat1")
    counter.train(document2.toIterator, "cat2")
    counter.train(document2.toIterator, "cat2")
    new BayesianClassifier(counter)
  }

  @Test def testWordProbablility {
    Assert.assertEquals(1.0, classifier.wordProbalility("mot1", "cat1"), 0.01)
    Assert.assertEquals(1.0, classifier.wordProbalility("mot1", "cat2"), 0.01)

    Assert.assertEquals(0.5, classifier.wordProbalility("mot2", "cat1"), 0.01)
    Assert.assertEquals(0.0, classifier.wordProbalility("mot2", "cat2"), 0.01)

    Assert.assertEquals(0.5, classifier.wordProbalility("mot3", "cat1"), 0.01)
    Assert.assertEquals(1.0, classifier.wordProbalility("mot3", "cat2"), 0.01)
  }

  @Test def testWordProbablility2 {
    val counter = Counter.empty
    counter.train("Je doit rapporter tout de suite cet album".split(" ").toIterator, "french")
    counter.train("Cet album appartient à Alice".split(" ").toIterator, "french")
    counter.train("Il y a du soleil et des nuages bleus".split(" ").toIterator, "french")
    counter.train("There is a blue sky today!".split(" ").toIterator, "english")
    counter.train("This album is awesome".split(" ").toIterator, "english")
    Assert.assertEquals(1, counter.countWordAppearIn("soleil", "french"))
    Assert.assertEquals(0, counter.countWordAppearIn("soleil", "english"))
    Assert.assertEquals(2, counter.countWordAppearIn("album", "french"))
    Assert.assertEquals(1, counter.countWordAppearIn("album", "english"))
    println("soleil" -> counter.wordCounter("soleil"))
    println("album" -> counter.wordCounter("album"))
    println(counter.categoryCounter)
    val classifier = new BayesianClassifier(counter)
    Assert.assertEquals(0.33, classifier.wordProbalility("soleil", "french"), 0.1)
    Assert.assertEquals(0.0, classifier.wordProbalility("soleil", "english"), 0.1)
    Assert.assertEquals(0.66, classifier.wordProbalility("album", "french"), 0.1)
    Assert.assertEquals(0.5, classifier.wordProbalility("album", "english"), 0.1)

    Assert.assertEquals(0.375, classifier.wordWeightedProbability("soleil", "spanish"), 0.1)
    Assert.assertEquals(0.416, classifier.wordWeightedProbability("soleil", "french"), 0.1)
  }


  @Test def testDocumentProbablility {
  }
}
