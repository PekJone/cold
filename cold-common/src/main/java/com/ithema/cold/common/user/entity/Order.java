package com.ithema.cold.common.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-22  19:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    public Long user;
    public String product;

    public int amount;
}
