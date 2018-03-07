package com.zhazha.sparkstreaming

import java.util.Date

object Test extends App {
  val now = new Date()
  val tomorrowMidnight = new Date(now.getYear,now.getMonth,now.getDate+1)
  println(tomorrowMidnight.getTime)
  val tmp = tomorrowMidnight.getTime - now.getTime
  println(tmp)

}
