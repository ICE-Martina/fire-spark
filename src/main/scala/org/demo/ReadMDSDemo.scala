package org.demo

import org.apache.spark.streaming.StreamingContext
import org.fire.spark.streaming.core.FireStreaming
import org.fire.spark.streaming.core.plugins.aliymns.AliyMNSSource

/**
  * Created by guoning on 2018/5/31.
  *
  */
object ReadMDSDemo extends FireStreaming {
  /**
    * 处理函数
    *
    * @param ssc
    */
  override def handle(ssc: StreamingContext): Unit = {

    val source = new AliyMNSSource(ssc)

    val logs = source.getMNSReceiverDStream[String](_.getMessageBodyAsRawString)

    logs.foreachRDD((rdd, time) => {
      rdd.take(10).foreach(println)
      source.updateOffsets(time.milliseconds)
    })
  }
}
