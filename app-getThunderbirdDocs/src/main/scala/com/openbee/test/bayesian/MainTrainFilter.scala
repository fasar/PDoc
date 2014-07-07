package com.openbee.test.bayesian

import java.io.File
import scala.io.Source
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule


/**
 * Created by fabien_s on 31/01/14.
 */
object MainTrainFilter {

  val categories = Configuration.categories

  val recordFolder = Configuration.recordFolder
  val dataFolder = Configuration.dataFolder
  val counterName = Configuration.counterName


  val counter = Counter.empty
  val tokenizer = new SimpleTokenizer()



  def main(args: Array[String]) {
    println("Train filter")
    val counter = getCounter
    println(s"My counter is : ")
    println("  counter.wordCounter : " + counter.wordCounter.size)
    println("  counter.categoryCounter : " + counter.categoryCounter.size)

    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)

    val outFic = new File(dataFolder, counterName)
    val output = mapper.writeValue(outFic, counter)

    val res = mapper.writeValueAsString(counter)
    println("Json result is : ")
    println(res.take(200))

  }

  def getCounter = {
    for(curCategory <- categories) {
      println("I'm training with category : " + curCategory)
      val listFiles = MainTrainFilter.listFiles(curCategory)
      val sizeListFiles = listFiles.size
      for((file: File, numFileM1) <- listFiles.zipWithIndex) {
        val numFile = numFileM1 +1
        println(s" [$numFile/$sizeListFiles] Training with category : " + curCategory)
        val source = Source.fromFile(file)
        val strFile = source.mkString
        val document = tokenizer.tokenize(strFile)
        counter.train(document, curCategory)
      }
    }
    counter
  }
  def listFiles(category:String): List[File] = {
    val dir = new File(recordFolder, category)
    dir.listFiles().toList.filter( _.getName.size > 10)
  }

}

