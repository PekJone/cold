package com.ithema.cold.common.pool;

import org.apache.tomcat.jni.Thread;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-18  9:55
 */
public class DynamicThreadPool {
    private ThreadPoolExecutor executor;
    private ScheduledExecutorService monitor ;

    public void startDynamicMonitoring(){
        monitor.scheduleAtFixedRate(()->{
            //监控指标
            int  activeCount = executor.getActiveCount();
            int  poolSize = executor.getPoolSize();
            long completedTaskCount = executor.getCompletedTaskCount();
            int queueSize = executor.getQueue().size();
            adjustPoolSize(activeCount,poolSize,queueSize);
        },0,5, TimeUnit.SECONDS);
    }

    private void adjustPoolSize(int activeCount, int poolSize, int queueSize) {

        if(queueSize>100 && poolSize <executor.getMaximumPoolSize()){
            executor.setCorePoolSize(Math.min(poolSize+2, executor.getMaximumPoolSize()));
        }

        if (activeCount<poolSize/2 && queueSize==0){
            executor.setCorePoolSize(Math.max(poolSize-1, executor.getCorePoolSize()));
        }
    }
}
