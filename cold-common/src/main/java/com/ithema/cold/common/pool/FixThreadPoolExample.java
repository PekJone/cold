package com.ithema.cold.common.pool;

import java.sql.SQLOutput;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  9:00
 */
public class FixThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.execute(()->{
                System.out.println("执行任务"+taskId+"线程"+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }
}
