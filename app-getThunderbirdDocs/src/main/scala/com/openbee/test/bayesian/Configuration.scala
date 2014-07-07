package com.openbee.test.bayesian

import java.io.File

/**
 * Created by fabien_s on 03/02/14.
 */
object Configuration {

  val feedPath = new File("""C:\Users\fabien_s\AppData\Roaming\Thunderbird\Profiles\ex1nbcas.default\Mail\Feeds""")
  val categories = List[String](
    "Afrique 94b85c2e",
    "Culture 4824671d",
    "Économie 1bc8f1c6",
    "Emploi 35890d9d",
    "Europe b89bea47",
    "Idées 30f55bf3",
    "Immobilier de2bcd20",
    "International 7565f27d",
    "Le Magazine cba873d6",
    "Livres ba67ada4",
    "Mobilité 5e12364c",
    "Planète 0272d815",
    "Politique 0a6f7ec9",
    "Santé 8657009c",
    "Sciences 54dd74a0",
    "Société db16fe9d",
    "Technologies a1b28b07",
    "Vous b77f61d8")


  val cacheFolder = new File("""data\cached""")
  cacheFolder.mkdirs()

  val recordFolder = new File("data")
  recordFolder.mkdirs()

  val dataFolder = new File("data")
  dataFolder.mkdirs()

  val counterName = "counter.json"

}
