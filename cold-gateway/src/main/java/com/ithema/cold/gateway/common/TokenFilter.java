package com.ithema.cold.gateway.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-05  17:14
 */

public class TokenFilter implements GlobalFilter, Ordered {
     Logger logger = LoggerFactory.getLogger(TokenFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!exchange.getRequest().getPath().value().equals("/cold/sys/user/login")){
            String token = exchange.getRequest().getHeaders().getFirst("token");
            if(token==null||token.isEmpty()){
                logger.info("token 为空");
                System.out.println("token为空");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
