package com.zhazha.sparkstreaming

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._


object DirectKafkaWordCount {
  def main(args: Array[String]) {
    //设置日志级别
    StreamingExamples.setStreamingLogLevels()

    val sparkConf = new SparkConf().setAppName("DirectKafkaWordCount").setMaster("local")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    ssc.checkpoint("checkpoint")

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

    // 每30S 获取一个前40S 的window
    val lines = messages.map(_.value).window(Seconds(40), Seconds(30))
    lines.filter(line=>line.contains("\u0001σ\u0001σmessage")).foreachRDD(rdd => {
      print("==================================")
      println(System.currentTimeMillis())
      println(rdd.count())
      rdd.foreach(println(_))
      println("==================================")

    })

    ssc.start()
    ssc.awaitTermination()
  }
}

// scalastyle:on println
