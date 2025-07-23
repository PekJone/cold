package com.itheima.cold.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-23  15:23
 */
public class ExplainTest {
    public static void main(String[] args) {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        StreamTableEnvironment streamTableEnvironment = StreamTableEnvironment.create(environment);

        List<Integer> list = new ArrayList<>();
        list.add(1);

        DataStream<Integer> stream = environment.fromCollection(list).flatMap(new FlatMapFunction<Integer, Integer>() {
            @Override
            public void flatMap(Integer integer, Collector<Integer> collector) throws Exception {
                collector.collect(integer*2);
            }
        }).setParallelism(2);
        //打印执行计划
        System.out.println(environment.getExecutionPlan());

        Table table = streamTableEnvironment.fromDataStream(stream,"num");
        System.out.println(streamTableEnvironment.explain(table));
    }
}
