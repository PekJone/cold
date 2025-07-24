package com.ithema.cold.common.orderSystem;



import io.micrometer.core.instrument.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-24  17:01
 */
public class SmartFallbackPolicy implements RejectedExecutionHandler {
    private final Logger logger = LoggerFactory.getLogger(SmartFallbackPolicy.class);
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (r instanceof FallBackTask){
            FallBackTask<?> task = (FallBackTask<?>) r;
            Metrics.counter("threadPool.rejected.tasks").increment();
            try{
                logger.warn("Task rejected ,executing fallback{}",r.toString());
                task.fallBack();
            }catch (Exception e){
                logger.error("Fallback execution failed");
                handleFallBackFailure(task,e);
            }
        }else {
            logger.warn("Non-fallback task rejected {}",r.toString());
            Metrics.counter("threadPool.rejected.standard.tasks").increment();
            throw new RejectedExecutionException("Task"+ r.toString()+" reject from"+ executor.toString());
        }
    }

    private void handleFallBackFailure(FallBackTask<?> task, Exception e) {
        logger.error("Critical: Fallback failed for task",e);
        Metrics.counter("threadPool.fallBack.failures").increment();
        System.out.println(task.toString()+e);
    }
}
