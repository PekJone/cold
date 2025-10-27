package com.concert.mail;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-21  17:01
 */
@Data
@Builder
public class MailResponse {

    private boolean success;
    private String messageId;
    private String errMessage;

    private Long costTime;
    private LocalDateTime sendTime ;

    public static MailResponse Success(String messageId,Long costTime){
           return MailResponse.builder()
                   .success(true)
                   .messageId(messageId)
                   .costTime(costTime)
                   .sendTime(LocalDateTime.now())
                   .build();
    }


    public static MailResponse failure(String errMessage,Long costTime){
           return MailResponse.builder()
                   .success(false)
                   .errMessage(errMessage)
                   .costTime(costTime)
                   .sendTime(LocalDateTime.now())
                   .build();

    }
}
