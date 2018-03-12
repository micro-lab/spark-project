package com.zhazha.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.internal.Logging

/**
  * 这个纯粹就是examples拿出来的东西
  */

object SparkExamples extends Logging {
  def setSparkLogLevels() {
    val log4jInitialized = Logger.getRootLogger.getAllAppenders.hasMoreElements
    if (!log4jInitialized) {
      // We first log something to initialize Spark's default logging, then we override the
      // logging level.
//      logInfo("Setting log level to [ERROR] for streaming example." +
//        " To override add a custom log4j.properties to the classpath.")
      logInfo("")
      Logger.getRootLogger.setLevel(Level.ERROR)
    }
  }
}
