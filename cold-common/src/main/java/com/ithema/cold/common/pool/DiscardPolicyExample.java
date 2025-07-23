package com.ithema.cold.common.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-23  18:58
 */
public class DiscardPolicyExample {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,1,0, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 0; i < 7; i++) {
            int taskID = i;
            executor.execute(()->sleepAndPrint("任务"+taskID));
            System.out.println("已提交任务："+taskID);
        }
    }

    public static void sleepAndPrint(String message){
        try {
            Thread.sleep(1000);
            System.out.println(message+"执行完成");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
