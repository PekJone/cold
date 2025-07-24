package com.ithema.cold.common.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-24  15:52
 */
public class RetryPolicyExample {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),((r, executor1) -> {
              try{
                  System.out.println("任务被拒绝，尝试重新放入队列");
                  if(!executor1.getQueue().offer(r,2,TimeUnit.SECONDS)){
                      System.err.println("重试失败，最终放弃任务");
                  }
              }catch (InterruptedException e){
                  Thread.currentThread().interrupt();
                  System.err.println("重试被中断");
              }
        }));

        for (int i = 1; i < 4; i++) {
            final int taskId = i;
            executor.execute(()->sleepAndPrint("task"+taskId,2000));
        }
        Thread.sleep(5000);
        executor.shutdown();
    }

    private static void sleepAndPrint(String s, int i) {
        try {
            Thread.sleep(i);
            System.out.println(s+"任务执行");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
