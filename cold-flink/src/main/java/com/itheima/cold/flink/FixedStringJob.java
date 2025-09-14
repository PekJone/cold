package com.itheima.cold.flink;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.util.Collector;

/**
 * @author benjamin_5
 * @Description 固定文本输出：统计词频
 * @date 2024/9/24
 */
public class FixedStringJob{

    private static final Log logger = LogFactory.getLog(FixedStringJob.class);

    // 启动本地flink ./bin/start-cluster.sh

    public static void main(String[] args) {
        try {
            // 创建执行环境
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            // 指定并行度,默认电脑线程数
            env.setParallelism(3);

            DataStream<String> stream = env.fromElements(
                    "Flink is a powerful framework",
                    "flink 是 一个 强大的 框架"
            );

            // 处理数据: 切换、转换、分组、聚合 得到统计结果
            SingleOutputStreamOperator<Tuple2<String, Integer>> result = stream
                    .flatMap(
                            (String value, Collector<Tuple2<String, Integer>> out) -> {
                                String[] words = value.split(            " ");
                                for (String word : words) {
                                    out.collect(Tuple2.of(word, 1));
                                }
                            }
                    )
                    // 显式地提供类型信息:对于flatMap传入Lambda表达式，系统只能推断出返回的是Tuple2类型，而无法得到Tuple2<String, Long>。只有显式设置系统当前返回类型，才能正确解析出完整数据
                    .returns(new TypeHint<Tuple2<String, Integer>>() {
                    })
                    .keyBy(value -> value.f0)
                    .sum(1);

            result.addSink(new RichSinkFunction<Tuple2<String, Integer>>() {
                @Override
                public void invoke(Tuple2<String, Integer> value, Context context) throws Exception {
                    String world = value.getField(0);
                    Integer count = value.getField(1);
                    // 打印
                    System.out.println("单词："+world + "，次数："+count);
                }
            });

            System.out.println("执行完成");
            // 执行
            env.execute("fixed text stream job");

        }catch (Exception e){
            e.printStackTrace();
            logger.error("流任务执行失败：", e);
        }
    }
}
