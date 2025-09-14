package com.concert.config;

import com.concert.dto.UserRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  10:36
 */
@Configuration
public class ThreadPoolConfig {
    @Bean(name = "scheduledExecutor")
    public ScheduledExecutorService scheduledExecutorService(){
        return Executors.newScheduledThreadPool(5);
    }
    @Bean(name="ticketProcessingPool")
    public ExecutorService ticketProcessingPool(){
        return Executors.newFixedThreadPool(10);
    }

    @Bean(name = "userRequestQueue")
    public PriorityBlockingQueue<UserRequest> userRequestQueue(){
        return new PriorityBlockingQueue<>(1000, Comparator.comparingInt(UserRequest::getPriority).reversed());
    }
}
