package com.openbee.test.bayesian

import scala.collection.mutable
import com.fasterxml.jackson.annotation.{JsonTypeInfo, JsonIgnoreProperties}
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * Created by fabien_s on 23/01/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Counter(
               var wordCounter: mutable.Map[Word, mutable.Map[Category, Count]],
               @JsonDeserialize(as=classOf[mutable.Map[Category, Long]])
               var categoryCounter: mutable.Map[Category, Count]
               ) {

  def train(document: Document, category: Category) = {
    val wordsAlreadyDone = mutable.HashSet.empty[Word]

    for(word: Word <- document if(!wordsAlreadyDone.contains(word))) {
      incWordCategory(word, category)
      wordsAlreadyDone.add(word)
    }
    incCategory(category)
  }

  private def incWordCategory(word: Word, category: Category) {
    val newWordCategoryCount: Count = countWordAppearIn(word, category) + 1

    val curCategory: mutable.Map[Category, Count] = wordCounter.getOrElse(word, mutable.Map.empty)
    curCategory.put(category, newWordCategoryCount)
    wordCounter.put(word, curCategory)
  }

  private def incCategory(category: Category) {
    val myCategoryCount: Count = getNbTrainOf(category)
    categoryCounter += (category -> (myCategoryCount + 1) )
  }

  def countWordAppearIn(word: Word, category: Category): Count = {
    (for( categoryCount <- wordCounter.get(word);
          count <- categoryCount.get(category))
    yield {count }).getOrElse(0)
  }

  def countWordAppear(word: Word): Count = {
    wordCounter(word).foldRight(0){ case ((cat, count), acc) => acc + count }
  }

  def getNbTrain(): Count = {
    categoryCounter.foldRight(0) { case ((category, categroryCount), acc) => categroryCount + acc }
  }

  def getNbTrainOf(category: Category): Count = {
    val res: Count= categoryCounter.getOrElse(category, 0)
    res
  }

  def getCategories(): Iterator[Category] = {
    categoryCounter.toIterator.map {case (category, count) => category }
  }

}


object Counter {
  def empty: Counter = new Counter( mutable.Map.empty[Word, mutable.Map[Category, Count]], mutable.Map.empty[Category, Count])
}