package com.ithema.cold.common.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-23  18:50
 */
public class CallerRunsPolicy {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.CallerRunsPolicy());
        System.out.println("主线程+：" + Thread.currentThread().getName());
        executor.execute(() -> sleepAndPrint("task 1"));
        executor.execute(() -> sleepAndPrint("task 1"));
        executor.execute(() -> sleepAndPrint("task 1"));

        executor.shutdown();
      }
    public static void sleepAndPrint(String message){
        try {
            Thread.sleep(1000);
            System.out.println(message+"由线程："+Thread.currentThread().getName()+"执行");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
