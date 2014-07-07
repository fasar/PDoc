package com.openbee.test.bayesian

import org.junit._
import scala.collection.mutable

/**
 * Created by fabien_s on 24/01/14.
 */

@Test
class CounterTest {

  var counter: Counter = null

  @Before
  def init() {
    val wordCounter = mutable.Map.empty[Word, mutable.Map[Category, Count]]
    val categoryCounter = mutable.Map.empty[Category, Count]
    wordCounter.put("mot1", mutable.Map[Category, Count]( "cat1" -> 5, "cat2" -> 3) )
    wordCounter.put("mot2", mutable.Map[Category, Count]( "cat1" -> 2, "cat2" -> 4) )
    categoryCounter += "cat1" -> 7
    categoryCounter += "cat2" -> 9
    counter = new Counter(wordCounter, categoryCounter)
  }


  @Test
  def testCountWordIn() {
    Assert.assertEquals(5, counter.countWordAppearIn("mot1", "cat1"))
    Assert.assertEquals(3, counter.countWordAppearIn("mot1", "cat2"))
    Assert.assertEquals(2, counter.countWordAppearIn("mot2", "cat1"))
    Assert.assertEquals(4, counter.countWordAppearIn("mot2", "cat2"))
  }
  @Test
  def testHowManyTimeTrained {
    Assert.assertEquals(7, counter.getNbTrainOf("cat1"))
    Assert.assertEquals(9, counter.getNbTrainOf("cat2"))
  }

  @Test
  def testTrain() {
    counter.train(List("mot2", "mot2", "mot1").toIterator, "cat1")
    Assert.assertEquals(6, counter.countWordAppearIn("mot1", "cat1"))
    Assert.assertEquals(3, counter.countWordAppearIn("mot1", "cat2"))
    Assert.assertEquals(3, counter.countWordAppearIn("mot2", "cat1"))
    Assert.assertEquals(4, counter.countWordAppearIn("mot2", "cat2"))
    Assert.assertEquals(8, counter.getNbTrainOf("cat1"))
    Assert.assertEquals(9, counter.getNbTrainOf("cat2"))
    Assert.assertEquals(17, counter.getNbTrain())
    counter.train(List("mot2", "mot2", "mot1").toIterator, "cat2")
    Assert.assertEquals(6, counter.countWordAppearIn("mot1", "cat1"))
    Assert.assertEquals(4, counter.countWordAppearIn("mot1", "cat2"))
    Assert.assertEquals(3, counter.countWordAppearIn("mot2", "cat1"))
    Assert.assertEquals(5, counter.countWordAppearIn("mot2", "cat2"))
  }


  @Test
  def testGetCategories {
    val categories = counter.getCategories()
    Assert.assertEquals(Set("cat1", "cat2"), categories.toSet)
  }


  @Test
  def testNbTrain {
    Assert.assertEquals(16, counter.getNbTrain())
  }

  @Test
  def testNewCounter() {
    val counter = Counter.empty
    counter.train(List("un", "deux").toIterator, "cat1")
    counter.train(List("un", "trois").toIterator, "cat2")
    println(counter.wordCounter)
    println(counter.categoryCounter)
  }

}
