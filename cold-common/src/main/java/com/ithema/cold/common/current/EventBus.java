package com.ithema.cold.common.current;


import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 事件系统总线   使用 CopyOnWriteArrayList 管理事件监听器
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-19  9:25
 */
public class EventBus {
    private final CopyOnWriteArrayList<EventListener> listeners = new CopyOnWriteArrayList<>();
    private final ExecutorService executors = Executors.newCachedThreadPool();

    /**
     * 注册事件监听器
     * @param listener
     */
    public void registerListener(EventListener listener){
        listeners.add(listener);
        System.out.println("监听器注册 "+listener.getName()+"当前监听器数量:"+listeners.size());
    }

    public void unregisterListener(EventListener listener){
        if (listeners.remove(listener)){
            System.out.println("监听器注销"+listener.getName()+"当前监听器数量:"+listeners.size());
        }
    }

    /**
     * 发布事件  异步非阻塞...
     * @param event
     */
    public void publicEvent(Event event){
        for (EventListener listener : listeners){
            executors.submit(()->{
                try{
                    listener.onEvent(event);
                }catch (Exception e){
                    System.out.println("监听器"+listener.getName()+"处理异常事件"+e.getMessage());
                }

            });
        }
    }
    public void publicEventSync(Event event){
        for (EventListener listener : listeners){
            try {
                listener.onEvent(event);
            }catch (Exception e){
                System.out.println("监听器"+listener.getName()+"处理异常事件"+e.getMessage());
            }
        }
    }

    public void shutdown(){
        executors.shutdown();
        try {
            if (!executors.awaitTermination(5, TimeUnit.SECONDS)){
                executors.shutdown();
            }
        }catch (InterruptedException e){
            executors.shutdown();
            Thread.currentThread().interrupt();
        }
    }


    public interface EventListener{
        void onEvent(Event event);
        String getName();
    }

    public static abstract class Event{
        private final String type;

        private final long timestamp;

        protected Event(String type) {
            this.type = type;
            this.timestamp = System.currentTimeMillis();
        }
        public String getType(){
            return type;
        }
        public long getTimestamp(){
            return timestamp;
        }
    }

    public static class UserLoginEvent extends Event{
        private final String username;
        protected UserLoginEvent(String username) {
            super("USER_LOGIN");
            this.username = username;
        }

        public String getUsername(){
            return username;
        }
    }

    public static class OrderCreateEvent extends Event{
        private final String orderId;

        private final double amount;
        protected OrderCreateEvent(String orderId,double amount) {
            super("ORDER_CREATE");
            this.orderId =orderId;
            this.amount = amount;
        }

        public double getAmount() {
            return amount;
        }

        public String getOrderId() {
            return orderId;
        }
    }

    public static class LoggingListener implements EventListener{
        @Override
        public void onEvent(Event event) {
            System.out.println("[日志监听器]事件类型"+event.getType()+"时间"+event.getTimestamp());
            try{
                TimeUnit.MILLISECONDS.sleep(50);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public String getName() {
            return "LoggingListener";
        }
    }

    public static class MetricListener implements EventListener {
        @Override
        public void onEvent(Event event) {
            if (event instanceof UserLoginEvent){
                UserLoginEvent loginEvent = (UserLoginEvent) event;
                System.out.println("指标监控器[用户登录]"+loginEvent.getUsername());;
            } else if (event instanceof OrderCreateEvent) {
                OrderCreateEvent orderCreateEvent = (OrderCreateEvent) event;
                System.out.println("指标监控器[创建订单]"+orderCreateEvent.getOrderId()+"，金额"+orderCreateEvent.getAmount());
            }
            try {
                TimeUnit.MILLISECONDS.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String getName() {
            return "MetricListener";
        }
    }


    public static void main(String[] args) throws InterruptedException{
         EventBus eventBus = new EventBus();

         LoggingListener loggingListener = new LoggingListener();
         MetricListener metricListener = new MetricListener();

         eventBus.registerListener(loggingListener);
         eventBus.registerListener(metricListener);

         Runnable publisherTask=()->{
             for (int i=0;i<5;i++){
                 if(i%2==0){
                     eventBus.publicEvent(new UserLoginEvent("user"+Thread.currentThread().getName()+"-"+i));
                 }else{
                     eventBus.publicEvent(new OrderCreateEvent("order"+Thread.currentThread().getName()+"-"+i,100*i));
                 }
                 try {
                     TimeUnit.MILLISECONDS.sleep(100);
                 } catch (InterruptedException e) {
                     Thread.currentThread().interrupt();
                     break;
                 }
             }
         };

         Thread pub1 = new Thread(publisherTask,"Publisher-1");
         Thread pub2 = new Thread(publisherTask,"Publisher-2");

         pub1.start();
         pub2.start();
        TimeUnit.SECONDS.sleep(1);
        EventListener newListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                System.out.println("[动态添加的监听器] 处理事件: " + event.getType());
            }

            @Override
            public String getName() {
                return "DynamicListener";
            }
        };
        eventBus.registerListener(newListener);

        pub1.join();
        pub2.join();

        TimeUnit.SECONDS.sleep(1); // 等待所有事件处理完成
        eventBus.shutdown();

    }

}
