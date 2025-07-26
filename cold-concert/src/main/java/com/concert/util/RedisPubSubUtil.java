package com.concert.util;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  16:59
 */
public class RedisPubSubUtil {
    private final RedisTemplate<String,Object> redisTemplate;
    private final RedisMessageListenerContainer container;
    private final Map<String, Consumer<String>> channelHandlers = new HashMap<>();

    public RedisPubSubUtil(RedisTemplate<String,Object> redisTemplate,RedisMessageListenerContainer container){
        this.redisTemplate = redisTemplate;
        this.container = container;
    }
    @PostConstruct
    public void init(){
        container.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] pattern) {
                String channel = new String(message.getChannel());
                String msg = new String(message.getBody());
                Consumer<String> handler = channelHandlers.get(channel);
                if (handler!=null){
                    handler.accept(msg);
                }
            }
        },new ChannelTopic("ticket_release"));
    }

    public void subscribe(String channel,Consumer<String> handler){
        channelHandlers.put(channel,handler);
    }

    public void publish(String channel,String message){
        redisTemplate.convertAndSend(channel,message);
    }
}
