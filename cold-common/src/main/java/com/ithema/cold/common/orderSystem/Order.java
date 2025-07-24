package com.ithema.cold.common.orderSystem;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-24  16:32
 */
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 订单实体类
 */
public class Order {
    private String orderId;
    private String userId;
    private BigDecimal amount;
    private String paymentType;
    private Date createTime;
    private Map<String, Object> extraParams;
    private String status;

    // 构造方法、getter和setter
    public Order() {}

    public Order(String orderId, String userId, BigDecimal amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.createTime = new Date();
        this.status = "CREATED";
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Map<String, Object> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, Object> extraParams) {
        this.extraParams = extraParams;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}