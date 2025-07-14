package com.ithema.cold.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-07  9:17
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ithema.cold.common.admin.dao")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}
