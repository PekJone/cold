package com.concert.service;

import com.concert.dto.UserRequest;
import com.concert.util.RedisPubSubUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-26  9:10
 */
public class RedisQueueServiceImpl extends QueueService{
    private static final String REQUEST_QUEUE_KEY = "ticket:requests";
    private static final String PROCESSING_QUEUE_KEY = "ticket:processing";
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisPubSubUtil redisPubSubUtil;

    @Override
    public void addRequest(UserRequest request) {
        try {
            String requestJson = objectMapper.writeValueAsString(request);
            redisTemplate.opsForList().rightPush(REQUEST_QUEUE_KEY,requestJson);
            redisPubSubUtil.publish("ticket_request","new");
        }catch (JsonProcessingException e){
            throw  new RuntimeException("序列化请求失败");
        }
    }

    @Override
    public void startProcessing() {
        //每个节点启动自己的处理线程
        new Thread(this::processQueue).start();
    }

    private void processQueue() {
        while (true){
            try{
                Object requestJson = redisTemplate.opsForList().rightPopAndLeftPush(REQUEST_QUEUE_KEY,PROCESSING_QUEUE_KEY,30, TimeUnit.SECONDS);
                if (requestJson!=null){
                    UserRequest request = objectMapper.readValue(requestJson.toString(), UserRequest.class);
                    handleRequest(request);
                    redisTemplate.opsForList().remove(PROCESSING_QUEUE_KEY,0,requestJson);
                }
            }catch (Exception e){
                e.printStackTrace();
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e2){
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    private void handleRequest(UserRequest userRequest){
        System.out.println("处理请求:"+userRequest);
    }
}
