package com.zhazha.sparkstreaming

import java.util.Date

import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.sql._

object DirectKafkaWordCount {
  /**
    * 获取下次时间与当前时间的间隔
  * */
  def resetTime(): Long = {
    val now = new Date()
    val nextTime = new Date(now.getYear, now.getMonth, now.getDate, now.getHours, now.getMinutes + 1, 0)
    nextTime.getTime - now.getTime


  }
/**
* 打印当前时间，没啥用
* */
  def getNow(): String = {
    val now = new Date()
    now.getHours + "-" + now.getMinutes + "-" + now.getSeconds
  }


  def main(args: Array[String]) {
    //设置日志级别
    StreamingExamples.setStreamingLogLevels()

    val sparkConf = new SparkConf().setAppName("DirectKafkaWordCount").setMaster("local")
    val ssc = new StreamingContext(sparkConf, Minutes(5))
    ssc.checkpoint("checkpoint")

    val sqlContext = new SQLContext(ssc.sparkContext)
    import sqlContext.implicits._

    //定义broker topic
    val Array(brokers, topics) = Array("10.125.149.50:9092,10.125.149.51:9092,10.125.149.52:9092,10.125.149.53:9092,10.125.149.54:9092", "chaos_log")

    // Create direct kafka stream with brokers and topics
    val topicsSet = topics.split(",").toSet
    //这一步，如果没有配置文件，官方的例子走不通的，需要添加bootstrap.servers,key.deserializer,value.deserializer,group.id
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers, "bootstrap.servers" -> brokers, "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer", "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer", "group.id" -> "test")
    val messages = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topicsSet, kafkaParams))



    val lines = messages.map(_.value).filter(x => x.contains("\u0001σ\u0001σmessage"))
      .map(line => line.split("σσmessage")(1).split('|'))
      .map(arr => (arr(1), arr(2), arr(3), arr(4), arr(5), arr(6), arr(7), arr(8), arr(9), arr(10), arr(11)))
      .window(Minutes(50),Minutes(30))
    lines.foreachRDD(rdd => {
      val messageDF = rdd.map(m => messagelog(m._1, m._2, m._3, m._4, m._5, m._6, m._7, m._8, m._9, m._10, m._11)).toDF

      messageDF.registerTempTable("message_log")

      println("==================================")
      //获取开始时间和结束时间
      val startTime = new DataUtil().getStartTime
      val endTime = startTime + 1800000
      println("开始时间:" + startTime + " 结束时间:" + endTime)


      val rs = sqlContext.sql("select count(distinct fromId),count(fromId) from message_log where appid='gomeplus_pro' and messageTime >='" + startTime + "' and messageTime <='" + endTime + "'")
      rs.foreach(println(_))
      println("==================================")

    })


    ssc.start()
    ssc.awaitTermination()
  }


}

case class messagelog(messageid: String, fromId: String, platformtype: String, appid: String, grouptype: String, messageType: String, toId: String, groupId: String, manufactureType: String, directionType: String, messageTime: String)
