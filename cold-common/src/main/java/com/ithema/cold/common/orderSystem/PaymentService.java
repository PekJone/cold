package com.ithema.cold.common.orderSystem;

import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-24  16:31
 */
public interface PaymentService {
    /**
     * 处理支付请求
     */
    PaymentResult processPayment(Order order);

    /**
     * 处理支付请求 带额外参数
     * @param order
     * @param extraParams
     * @return
     */
    PaymentResult processPayment(Order order, Map<String,Object> extraParams);

    /**
     * 查询支付状态
     * @param orderId
     * @return
     */
    PaymentResult queryPaymentStatus(String orderId);

    /**
     * 取消支付
     * @param orderId
     * @return
     */
    PaymentResult cancelPayment(String orderId);

    /**
     * 退款
     * @param orderId
     * @param refundAmount
     * @return
     */
    PaymentResult refund(String orderId,int refundAmount);

    /**
     * 获取支付渠道列表
     * @return
     */
    Map<String,String> getAvailableChannels();

    /**
     * 验证支付通知签名
     * @param notification
     * @return
     */
    boolean verifyNotification(Map<String,String> notification);
}
