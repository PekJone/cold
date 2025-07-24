package com.ithema.cold.common.orderSystem;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-24  16:49
 */
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 订单队列管理器
 */
public class OrderQueueManager {
    private static final BlockingQueue<Order> pendingOrders = new LinkedBlockingQueue<>(1000);

    public static void addPendingOrder(Order order) {
        try {
            pendingOrders.put(order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("添加待处理订单被中断", e);
        }
    }

    public static Order takePendingOrder() throws InterruptedException {
        return pendingOrders.take();
    }

    public static int getPendingOrderCount() {
        return pendingOrders.size();
    }
}
