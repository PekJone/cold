package com.ithema.cold.common.orderSystem;

import org.aspectj.weaver.ast.Not;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-24  16:25
 */
public class OrderPaymentTask implements FallBackTask<PaymentResult>{
    private final Order order;
    private final PaymentService paymentService;
    private PaymentResult result;
    private Exception exception;

    public OrderPaymentTask(Order order,PaymentService paymentService){
        this.order = order;
        this.paymentService = paymentService;
    }

    @Override
    public void execute() {
        result = paymentService.processPayment(order);
        PaymentLogger.logSuccess(order.getOrderId(),result.getTransactionId());
    }

    @Override
    public void fallBack() {
       //降级逻辑  将订单修改为待确认状态
        result = new PaymentResult();
        result.setSuccess(false);
        result.setStatusCode("SYSTEM_BUSY");
        result.setMessage("系统繁忙，您的订单已接收，支付状态为待确认");

        OrderQueueManager.addPendingOrder(order);

        NotificationService.sendSms(order.getUserId(), "你的订单："+order.getOrderId()+"已接收，支付结果待确认");

        PaymentLogger.logFallback(order.getOrderId(), "线程池拒绝");
    }

    @Override
    public PaymentResult getResult() {
        return result;
    }

    @Override
    public Exception getException() {
        return exception;
    }
}
