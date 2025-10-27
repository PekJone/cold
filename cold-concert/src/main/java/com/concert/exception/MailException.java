package com.concert.exception;

import com.concert.mail.MailRequest;
import lombok.Data;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-23  18:18
 */
@Data
public class MailException extends RuntimeException{
    private final String errorCode ;

    private final MailRequest mailRequest;

    public MailException(String message){
        super(message);
        this.errorCode = "Mail-001";
        this.mailRequest = null;
    }

    public MailException(String errorCode,String message, MailRequest mailRequest){
        super(message);
        this.errorCode = errorCode;
        this.mailRequest = mailRequest;
    }

    public MailException(String message,Throwable cause){
        super(message);
        this.errorCode="Mail-002";
        this.mailRequest = null;
    }
}
