package com.zhazha.test

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.util.Random

object Test extends App {
  val ab = ArrayBuffer[Int]()

  for (i <- 1 to 1000000) {
    val ran = Random.nextInt(10000)
    ab += ran
  }
  val arr = ab.toArray
  println(System.currentTimeMillis())
  new QuickSort().quickSort(arr.toArray, 0, arr.length - 1)
  println(System.currentTimeMillis())
  //  arr.foreach(println(_))


  val lb = ListBuffer[Int]()
  for (i <- 1 to 10) {
    val ran = Random.nextInt(100)
    lb += ran
  }

}
