package com.ithema.cold.common.orderSystem;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-24  16:28
 */
public class PaymentResult implements Serializable {
    private static final long serialVersionUID = 1L;

    // 支付状态码
    private String statusCode;

    // 是否支付成功
    private boolean success;

    // 支付结果描述信息
    private String message;

    // 交易/支付编号
    private String transactionId;

    // 支付渠道（微信/支付宝/银联等）
    private String paymentChannel;

    // 支付金额（单位：分）
    private int amount;

    // 支付完成时间
    private Date paymentTime;

    // 支付货币类型（默认CNY）
    private String currency = "CNY";

    // 第三方支付平台返回的原始数据
    private String rawResponse;

    // 扩展字段，用于存储额外信息
    private Object extraData;

    // 构造方法
    public PaymentResult() {
    }

    public PaymentResult(boolean success, String statusCode, String message) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
    }

    // Getter 和 Setter 方法
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

    // 实用方法
    public boolean isPending() {
        return "PENDING".equals(statusCode);
    }

    public boolean isFailed() {
        return !success && !isPending();
    }

    @Override
    public String toString() {
        return "PaymentResult{" +
                "statusCode='" + statusCode + '\'' +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", paymentChannel='" + paymentChannel + '\'' +
                ", amount=" + amount +
                ", paymentTime=" + paymentTime +
                ", currency='" + currency + '\'' +
                ", rawResponse='" + (rawResponse != null ? "***masked***" : null) + '\'' +
                ", extraData=" + extraData +
                '}';
    }
}
