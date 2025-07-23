package com.itheima.cold.flink;

import com.alibaba.fastjson.JSON;
import com.ithema.cold.common.netty.MessageEntity;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

import java.util.Properties;

public class StreamingJob {
    public static void main(String[] args) throws Exception{
        // kafka队列名称
        String kafka_topic = "wang";

        // 获取环境信息
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        //设置容错
        env.enableCheckpointing(5000);

        //设置检查点模式
        env.getCheckpointConfig().setCheckpointingMode(CheckpointConfig.DEFAULT_MODE);

        //设置重启策略
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 1000));

        //kafka队列属性
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "1.15.15.159:9093");
		properties.setProperty("group.id", "coldflink");
        properties.setProperty("auto.offset.reset", "earliest");

        //kafka队列consumer，接收netty发送过来的数据
        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<String>(kafka_topic, new SimpleStringSchema(), properties);
        consumer.setStartFromLatest();

        //设置实时数据源：kafka队列
        DataStream<MessageEntity> stream = env.addSource(consumer)
                .setParallelism(1)
                .map(string  -> JSON.parseObject(string, MessageEntity.class));

        //从redis中获取设备信息，合并至消息中
        DataStream<MessageEntity> outStream = stream.map(new MessageAggregate());

        //存储设备指标消息
        outStream.addSink(new SinkToMySQL());

        // 将消息发送到kafka队列
        // Druid监听此队列，将消息保存到druid中
        FlinkKafkaProducer<String> myProducer = new FlinkKafkaProducer<String>("all_device_message", new SimpleStringSchema(), properties);
        outStream.map(new MapFunction<MessageEntity, String>() {
            @Override
            public String map(MessageEntity value) throws Exception {
                return JSON.toJSONString(value);
            }
        }).addSink(myProducer);
        myProducer.close();

        env.execute("冷链设备实时监控任务");
    }



}
