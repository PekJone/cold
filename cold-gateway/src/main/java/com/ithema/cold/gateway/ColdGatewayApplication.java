package com.ithema.cold.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.swing.*;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-05  16:54
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ColdGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ColdGatewayApplication.class,args);
    }
}
