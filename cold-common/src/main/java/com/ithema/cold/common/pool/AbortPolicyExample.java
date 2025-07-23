package com.ithema.cold.common.pool;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-23  18:34
 */
public class AbortPolicyExample {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,1,0, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1),new ThreadPoolExecutor.AbortPolicy());
        try {
            executor.execute(()->sleepAndPrint("task 1"));
            executor.execute(()->sleepAndPrint("task 2"));
            executor.execute(()->sleepAndPrint("task 3"));
        }catch (RejectedExecutionException e){
            System.err.println("任务被拒绝"+e.getMessage());
        }finally {
            executor.shutdown();
        }

    }

    private static void sleepAndPrint(String message) {
        try {
            Thread.sleep(1000);
            System.out.println(message+"执行完成");
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
