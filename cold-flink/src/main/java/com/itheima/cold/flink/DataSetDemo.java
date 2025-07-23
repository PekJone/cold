package com.itheima.cold.flink;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-22  16:44
 */
public class DataSetDemo {
    public static void main(String[] args) throws Exception{
        //从集合中创建dataSet


        //从文件中创建dataset

        //设置运行环境
        ExecutionEnvironment environment =ExecutionEnvironment.getExecutionEnvironment();

        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("def");
        DataSet<String> dataSet = environment.fromCollection(list);
        dataSet.print();
        DataSet dataSet2 = environment.readTextFile(
"D:\\tool\\mongdbdata\\Springboot3\\cold-manager\\logs\\cold-admin.log");
        dataSet2.print();
    }
}
