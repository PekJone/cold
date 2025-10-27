package com.concert.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.Properties;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-21  11:03
 */
@ConfigurationProperties(prefix = "enterprise.mail")
@Data
@Validated
public class MailProperties {
    private String host ="smtp.qq.com";
    private int port = 587;

    private String username ;
    private String password ;

    private String protocol = "smtp";

    private String defaultEncoding = "UTF-8";

    private Pool pool = new Pool();
    private Properties properties = new Properties();

    @Data
    public class Pool{
        private Integer maxSize = 10;
        private Integer timeOut = 5000;
        private Integer maxWait = 300;
    }
    private Template template = new Template();

    @Data
    public static class Template {
        private String prefix = "classpath:/mail-templates/";
        private String suffix = ".html";
        private String encoding = "UTF-8";
        private String mode = "HTML";
    }

}
