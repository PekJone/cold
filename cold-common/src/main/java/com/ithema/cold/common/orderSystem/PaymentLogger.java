package com.ithema.cold.common.orderSystem;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-24  16:49
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 支付日志记录器
 */
public class PaymentLogger {
    private static final Logger logger = LoggerFactory.getLogger(PaymentLogger.class);

    public static void logSuccess(String orderId, String transactionId) {
        logger.info("支付成功 - 订单ID: {}, 交易号: {}", orderId, transactionId);
        // 这里可以添加其他日志逻辑，如写入数据库等
    }

    public static void logFallback(String orderId, String reason) {
        logger.warn("支付降级处理 - 订单ID: {}, 原因: {}", orderId, reason);
        // 这里可以添加其他日志逻辑
    }

    public static void logError(String orderId, String message, Throwable e) {
        logger.error("支付处理错误 - 订单ID: {}, 错误: {}", orderId, message, e);
    }
}