package com.itheima.cold.flink;

import com.ithema.cold.common.user.entity.Person;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-22  9:49
 */

public class Example {
    public static void main(String[] args) throws Exception{
        final StreamExecutionEnvironment see = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Person> flinStone = see.fromElements(
                new Person("wang1",12),
                new Person("wang2",12),
                new Person("wang3",19)
                );

        DataStream<Person> adults = flinStone.filter(new FilterFunction<Person>() {
            @Override
            public boolean filter(Person person) throws Exception {
                return person.getAge()>18;
            }
        });

        System.out.println("这是我的测试结果：");
        adults.print();

        see.execute();
    }
}
