package com.itheima.cold.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;


/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-22  16:18
 */
public class BatchJob {
    public static void main(String[] args) throws Exception {
        //设置运行环境
        final ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<String> text = environment.fromElements(
                "abc abc def lksd disd","adf dedf def abc"
        );

        text.flatMap(new FlatMapFunction<String, Tuple2<String,Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                for (String word : s.split(" ")) {
                    collector.collect(new Tuple2<>(word,1));
                }
            }
        }).groupBy(0).sum(1).print();

       // environment.execute("Flink batch API SKeleton");
    }
}
