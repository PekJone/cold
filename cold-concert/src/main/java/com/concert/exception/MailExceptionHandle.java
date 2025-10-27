package com.concert.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-27  15:52
 */
@Slf4j
@ControllerAdvice
public class MailExceptionHandle {
    @ExceptionHandler(MailException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse>  handleMailException(MailException e){
        log.error("邮件服务异常:{}",e.getMessage(),e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                        .errorCode(e.getErrorCode())
                        .errorMessage(e.getMessage())
                        .timeStamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }


}
