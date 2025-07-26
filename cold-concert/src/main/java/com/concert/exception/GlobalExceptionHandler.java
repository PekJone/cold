package com.concert.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  16:17
 */
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public String handleBusinessException(BusinessException e){
        return "业务错误："+e.getMessage();
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e){
        return "系统错误："+e.getMessage();
    }

}
