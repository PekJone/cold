package com.ithema.cold.common.orderSystem;

import io.micrometer.core.instrument.util.NamedThreadFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-24  19:14
 */
public class OrderProcessingSystem {
    private final ThreadPoolExecutor orderProcessingPool;
    private final PaymentService paymentService;

    public OrderProcessingSystem(PaymentService paymentService){
        this.paymentService = paymentService;
        int corePoolSize = Runtime.getRuntime().availableProcessors()*2;
        int maxPoolSize = corePoolSize*4;
        int queueCapacity = 100;

        this.orderProcessingPool = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(queueCapacity),
                new NamedThreadFactory("order-processor"),
                new SmartFallbackPolicy()
        );
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }
    public CompletableFuture<PaymentResult> processOrderAsync(Order order){
        CompletableFuture<PaymentResult> future = new CompletableFuture<>();
        OrderPaymentTask task = new OrderPaymentTask(order,paymentService){
            @Override
            public void execute() {
                super.execute();
                future.complete(getResult());
            }

            @Override
            public void fallBack() {
                super.fallBack();
                future.complete(getResult());
            }
        };
        try{
            orderProcessingPool.execute(task);
        }catch (Exception e){
            future.completeExceptionally(e);
        }
        return future;
    }

    private void shutdown() {
        orderProcessingPool.shutdown();
        try {
            if(!orderProcessingPool.awaitTermination(60,TimeUnit.SECONDS)){
                orderProcessingPool.shutdown();
            }
        }catch (InterruptedException e){
            orderProcessingPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
