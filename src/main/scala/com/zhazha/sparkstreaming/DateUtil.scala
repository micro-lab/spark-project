package com.zhazha.sparkstreaming

import java.util.Date
import java.util.Calendar

class DataUtil {
  def getStartTime(): Long = {
    val date = new Date()
    val current_year = date.getYear
    val current_month = date.getMonth
    val current_day = date.getDate
    val current_hour = date.getHours
    val current_min = date.getMinutes
    var year = current_year
    var month = current_month
    var day = current_day
    var hour = current_hour
    var min = 0
    if (current_min < 30) {
      min = 30
      if (hour == 0) {
        hour = 23
        if (day == 1) {
          val calendar = Calendar.getInstance
          calendar.set(year, month, day - 1)
          day = calendar.getTime.getDate
          if (month == 1) {
            month = 12
            year = year - 1
          } else {
            month -= 1
          }
        } else {
          day -= 1
        }
      } else {
        hour -= 1
      }
    } else {
      min = 0
    }
    return new Date(year, month, day, hour, min).getTime
  }
}