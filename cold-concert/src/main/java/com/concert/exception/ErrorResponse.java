package com.concert.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-27  16:05
 */
@Data
@Builder
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private LocalDateTime timeStamp;
}
