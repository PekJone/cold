package com.concert.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  10:58
 */
@Data
@AllArgsConstructor
public class RequestResult {
    private final int userId;
    private final boolean success;
    private final String message;

}
