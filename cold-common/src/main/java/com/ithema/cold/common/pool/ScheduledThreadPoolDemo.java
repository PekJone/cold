package com.ithema.cold.common.pool;

import ch.qos.logback.core.util.TimeUtil;

import java.sql.Time;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  9:12
 */
public class ScheduledThreadPoolDemo {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        //延迟3秒执行的任务
        executor.schedule(()->{
            System.out.println("延迟3秒执行的任务"+Thread.currentThread().getName());
        },3, TimeUnit.SECONDS);
        //延迟两秒 每隔一秒执行一次任务
        executor.scheduleAtFixedRate(()->{
            System.out.println("周期性执行的任务，线程"+Thread.currentThread().getName());
        },2,1,TimeUnit.SECONDS );

        executor.schedule(()->{
            executor.shutdown();
            System.out.println("线程池已关闭");
        },10,TimeUnit.SECONDS);
    }
}
