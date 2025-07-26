package com.concert.exception;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  16:16
 */
public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}
