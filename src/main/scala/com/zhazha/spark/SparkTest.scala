package com.zhazha.spark

import com.zhazha.scala.FuctionTests.myMatrix
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.util.collection.CompactBuffer

import scala.Array.ofDim

object SparkTest extends App {

  SparkExamples.setSparkLogLevels()
  val spark = SparkSession.builder.appName("test").master("local").getOrCreate()
  val sc = spark.sparkContext

  val data = sc.textFile("C:\\Users\\tanyongkai\\Desktop\\spark\\xx.csv")

  val re = data.map(line => (line.split("\t")(0).toInt, line.split("\t")(1), line.split("\t")(2), line.split("\t")(3).toInt))
    .map(line => (line._3, line._4))
    .reduceByKey(_ + _)

  val re1 = data.map(line => (line.split("\t")(0).toInt, line.split("\t")(1), line.split("\t")(2), line.split("\t")(3).toInt))
    .map(line => (line._2+"_"+line._3, line._4))
  re1.foreach(println(_))




}
