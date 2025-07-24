package com.ithema.cold.common.orderSystem;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-24  16:56
 */
/**
 * 通知服务
 */
public class NotificationService {
    // 模拟短信发送
    public static void sendSms(String userId, String message) {
        System.out.printf("发送短信给用户%s: %s%n", userId, message);
        // 实际项目中这里会调用短信网关API
    }

    // 模拟邮件发送
    public static void sendEmail(String userId, String subject, String content) {
        System.out.printf("发送邮件给用户%s - 主题: %s, 内容: %s%n",
                userId, subject, content);
    }
}
