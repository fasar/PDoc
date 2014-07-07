package com.openbee.test.bayesian


import scala.io.Source
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import java.io.File


/**
 * Created by fabien_s on 31/01/14.
 */
object MainClassifyDocument {

  val dataFolder = Configuration.dataFolder
  val counterName = Configuration.counterName

  val categories = Configuration.categories

  def main(args: Array[String]) {
    val fic = new File(args(0))
    if(!fic.exists() && !fic.isFile) {
      throw new Exception("Can't open file : " + args(0))
    }
    val src = Source.fromFile(fic)
    val docSrc = src.getLines().mkString("\n")

    println("Classify document")
    val counter: Counter = loadCounter
    println("Counter categories = " + counter.categoryCounter.size + " words = " + counter.wordCounter.size )

    val classifier = new BayesianClassifier(counter)
    val tokenizer = new SimpleTokenizer()

    println("Document classification : ")
    for(category <- categories
        //if(category.startsWith("Planète"))
    ) {
      val docToClass = tokenizer.tokenize(docSrc)
      val docToClassList = docToClass.toList
      val probablility = classifier.documentProbablility(docToClassList.toIterator, category)
      val mulProba = Math.pow(probablility, 1.0 / docToClassList.size)
      println(s"$category : ${mulProba}")
    }

  }


  def loadCounter: Counter = {
    println("Start doing counter")

    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)

    val inFic = new File(dataFolder, counterName)
    mapper.readValue(inFic, classOf[Counter])
  }


}
