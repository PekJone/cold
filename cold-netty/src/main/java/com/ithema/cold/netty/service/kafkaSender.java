package com.ithema.cold.netty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-19  8:41
 */
@Component
public class kafkaSender {

    private KafkaTemplate<String,String> kafkaTemplate ;
    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public  boolean send(String topic,String message){
        try {
            kafkaTemplate.send(topic,message);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
