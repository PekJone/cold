package com.ithema.cold.common.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-23  19:08
 */
public class LogDiscardPolicyExample {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,1,0,
                TimeUnit.SECONDS,new ArrayBlockingQueue<>(1),(r, executor1) -> {
                System.err.println("任务"+r+"被拒绝，当前活跃线程："+executor1.getActiveCount()+"队列大小："+executor1.getQueue().size());
        });

        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            executor.execute(()->sleepAndPrint("task"+taskId));
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
