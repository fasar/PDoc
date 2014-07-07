package com.openbee.test.bayesian

/**
 * Created by fabien_s on 24/01/14.
 */
class BayesianClassifier(val counter:Counter) extends Classifier {

  private val weight = 3.0
  private val assumedProbability = 0.5

  private val nbCategories = counter.getCategories().size
  private val probaInCategory = (1 / nbCategories)
  private val probaOutCategory = ((nbCategories - 1) / nbCategories)

  /**
   * P(M|C) est la probabilité que le mot "M" apparaisse dans des catégories "C"
   *
   * @param word
   * @param category
   * @return
   */
  def probaWordKnowingCat(word: Word, category: Category): Probability = {
    val trained = counter.getNbTrainOf(category)
    val res = if(trained == 0) {
      0.0
    } else {
      val countWord = counter.countWordAppearIn(word, category)
      val elem = (countWord * 1.0)
      elem / trained
    }
    res
  }

  /**
   * P(C) est la probabilité dans l'absolu qu'un message quelconque soit dans une catégorie "C"
   *
   * @param category
   * @return
   */
  def probaInCategory(category: Category): Probability =  {
    probaInCategory
  }

  /**
   * P(H) est la probabilité dans l'absolu qu'un message quelconque ne soit pas de la catégorie "C"
   * @param category
   * @return
   */
  def probaOutCategory(category: Category): Probability =  {
    probaOutCategory
  }




  def wordProbalility(word: Word, category: Category): Probability = {
    val trained = counter.getNbTrainOf(category)
    val res = if(trained == 0) {
      0.0
    } else {
      val countWord = counter.countWordAppearIn(word, category)
      val elem = (countWord * 1.0)
      elem / trained
    }
    res
  }

  def wordWeightedProbability(word: Word, category: Category): Probability = {
    val basicProbability = wordProbalility(word, category)
    val totalWordAppear: Count = counter.countWordAppear(word)

    (( weight * assumedProbability ) + (totalWordAppear * basicProbability)) / (weight + totalWordAppear)
  }


  def categorieProbablilityKnowingDocument(document: Document, category: Category): Probability = {
    val res: Probability =
      (for(word <- document)
      yield {
        //wordWeightedProbability(word, category)
        val res = wordWeightedProbability(word, category)
        println(s"""$word : $res""")
        res
      }).foldRight(1.0) { case (nb ,acc) =>   nb * acc  }

    res
  }


  def documentProbablility(document: Document, category: Category): Probability = {
    //Pr(Category | Document) = Pr(Document | Category) x Pr(Category) / Pr(Document)

    val resAcc: Probability =
      (for(word <- document)
      yield {
        //wordWeightedProbability(word, category)
        val res = wordWeightedProbability(word, category)
        res
      }).foldRight(1.0) { case (nb ,acc) =>   nb * acc  }

    val resM1: Probability =
      (for(word <- document)
      yield {
        //wordWeightedProbability(word, category)
        val res = wordWeightedProbability(word, category)
        res
      }).foldRight(1.0) { case (nb ,acc) =>   (1-nb) * acc  }

    resAcc / ( resAcc + resM1)
  }
}
