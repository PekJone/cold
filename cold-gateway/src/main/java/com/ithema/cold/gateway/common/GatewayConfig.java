package com.ithema.cold.gateway.common;

import org.aspectj.weaver.patterns.IToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-05  17:14
 */
@Configuration
public class GatewayConfig {

    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }


}
