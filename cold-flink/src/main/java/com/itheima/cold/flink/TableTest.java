package com.itheima.cold.flink;

import com.ithema.cold.common.user.entity.Order;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-22  19:27
 */
public class TableTest {
    public static void main(String[] args) throws Exception {
        //定义执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        DataStream<Order> orderA = env.fromCollection(Arrays.asList(
                new Order(1L,"book1",3),
                new Order(2L,"book2",4),
                new Order(3L,"book3",6),
                new Order(4L,"book4",7)
        ));

        DataStream<Order> orderB = env.fromCollection(Arrays.asList(
                new Order(5L,"book1",8),
                new Order(6L,"book2",9),
                new Order(7L,"book1",10),
                new Order(8L,"book1",11)
        ));
        //将数据流转换成Flink Table
        Table tableA = tEnv.fromDataStream(orderA,"user,product,amount");
        Table tableB = tEnv.fromDataStream(orderB,"user,product,amount");

        Table result = tEnv.sqlQuery("select * from "+tableA+" where amount>2 union all "+"select * from "+tableB+" where amount >9");
        tEnv.toAppendStream(result, Order.class).print().setParallelism(1);
        env.execute();
    }
}
