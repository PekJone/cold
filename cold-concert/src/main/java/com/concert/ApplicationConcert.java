package com.concert;

import com.concert.service.QueueService;
import com.concert.service.TicketReleaseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Queue;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  10:32
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApplicationConcert {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationConcert.class,args);
        //启动票务释放
        TicketReleaseService releaseService = context.getBean(TicketReleaseService.class);
        releaseService.scheduleTicketReleases();

        //启动队列处理
        QueueService queueService = new QueueService();
        queueService.startProcessing();

        System.out.println("票务系统已启动");

    }
}
