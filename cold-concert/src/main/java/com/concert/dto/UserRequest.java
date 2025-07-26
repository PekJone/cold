package com.concert.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  10:43
 */
@Data
@AllArgsConstructor
public class UserRequest implements Comparable<UserRequest>{
    private final int userId;
    private final String ticketType;

    private final int quantity;

    private final int priority;
    @Override
    public int compareTo(UserRequest other){
       return Integer.compare(other.priority,this.priority);
    }
}
