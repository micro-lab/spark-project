package com.zhazha.test

class QuickSort {
  def quickSort(src: Array[Int], begin: Int, end: Int) {
    if (begin < end) {
      println(" begin:"+begin+" end:"+end)
      val key = src(begin)
      var i = begin
      var j = end
      while (i < j) {
        while (i < j && src(j) > key) {
          j -= 1
        }
        if (i < j) {
          src(i) = src(j)
          i += 1
        }
        while (i < j && src(i) < key) {
          i += 1
        }
        if (i < j) {
          src(j) = src(i)
          j -= 1
        }
      }
      src(i) = key

      quickSort(src, begin, i - 1)
      quickSort(src, i + 1, end)
    }
  }

}
