package com.zhazha.scala

import Array._

object FuctionTests extends App {
  //  函数柯里化(Currying),x默认值为100
  def minus(x: Int = 100)(y: Int): Double = y / x

  //  println(minus()(y = 100))

  //  匿名函数
  var incy = (y: String) => y + "haha"
  var incx = (x: Int) => {
    if (x > 100) {
      x / 2
    } else {
      x * 3
    }
  }
  //  println(incy("hehe"))
  //  println(incx(124))

  //高阶函数（Higher-Order Function）就是操作其他函数的函数。
  def hof(x: Int) = incx(x)

  //内嵌函数
  def factorial(i: Int): Int = {
    def fact(i: Int, accumulator: Int): Int = {
      if (i <= 1)
        accumulator
      else
        fact(i - 1, i * accumulator)
    }

    fact(i, 1)
  }


  /** 闭包
    * 闭包通常来讲可以简单的认为是可以访问一个函数里面局部变量的另外一个函数。
    * 这样定义的函数变量 multiplier 成为一个"闭包"，因为它引用到函数外面定义的变量，定义这个函数的过程是将这个自由变量捕获而构成一个封闭的函数。
    * */
  val factor = 10
  val multiplier = (i: Int) => i * factor


  /**
    * 申明一个数组Array[ObjectType]
    * //遍历Array
    * for (x <- arr) {
    * println(x)
    * }
    * //遍历Array
    *arr.foreach(println(_))
    */
  val arr: Array[String] = Array("zs", "ls")


  val arr1: Array[Int] = Array(1, 2, 3)
  //数组求和
  //  println(arr1.sum)
  //数组求最大值
  //  println(arr1.max)

  //数组遍历
  //  arr.foreach(println(_))
  //  for (x <- arr) {println(x)}
  //  val it = arr.iterator
  //  while(it.hasNext){
  //    println(it.next())
  //  }

  /**
    * 二维数组,矩阵
    */
  var myMatrix = ofDim[Int](3, 3)

  // 创建矩阵
  for (i <- 0 to 2; j <- 0 to 2) {
    myMatrix(i)(j) = j
  }
  // 打印二维阵列
  //  for (i <- 0 to 2) {
  //    for (j <- 0 to 2) {
  //      print(" " + myMatrix(i)(j))
  //    }
  //    println()
  //  }
  //区间数组
  var myList1 = range(10, 20, 2)
  //10,12,14,16,18
  //  myList1.foreach(print(_))
  var myList2 = range(10, 20) //10,11,12,13,14,15,16,17,18,19
  //  myList2.foreach(print(_))


}
