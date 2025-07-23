package com.itheima.cold.flink;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.Types;
import org.apache.flink.table.api.java.StreamTableEnvironment;

/**
 * @author 王朋飞
 * @version 1.0
 * @deprecated
 * 将DataStream转换成Table
 * 将DataStream注册成Table
 * 将Table转换成DataStream
 * @date 2025-07-23  15:35
 */
public class TransferTable {
    public static void main(String[] args) {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        StreamTableEnvironment streamTableEnvironment = StreamTableEnvironment.create(environment);

        DataStream<Tuple2<String,Integer>> stream = environment.fromElements(
                new Tuple2<>("hello",3),
                new Tuple2<>("world",4)
        );

        Table table = streamTableEnvironment.fromDataStream(stream,"word,count");

        streamTableEnvironment.registerDataStream("table1",stream);
        streamTableEnvironment.registerDataStream("table2",stream,"word,count");

        TupleTypeInfo<Tuple2<String,Integer>> tuple2TupleTypeInfo = new TupleTypeInfo<>(
                Types.STRING(),
                Types.INT()
        );
        DataStream<Tuple2<String,Integer>> dataStream= streamTableEnvironment.toAppendStream(table,tuple2TupleTypeInfo);
    }
}
