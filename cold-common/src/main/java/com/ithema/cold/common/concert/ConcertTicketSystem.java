package com.ithema.cold.common.concert;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  9:28
 */
public class ConcertTicketSystem {
    private static final AtomicInteger presaleTickets = new AtomicInteger(100); //预售票100张
    private static final AtomicInteger regularTickets = new AtomicInteger(200); //正式票200张
    private static final AtomicInteger lastMinuteTickets = new AtomicInteger(50); //最后抢票50张

    public static void main(String[] args) {
        System.out.println("演唱会票务系统启动。。。");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);

        //预售票 5秒后放出
        executorService.schedule(()->{
            System.out.println("==预售票开始放出==");
            scheduleAtFixedMonitoring("预售票",presaleTickets,executorService);
        },5,TimeUnit.SECONDS);
        executorService.schedule(() -> {
            System.out.println("\n=== 正式票开始放出 ===");
            scheduleAtFixedMonitoring("正式票", regularTickets, executorService);
        }, 10, TimeUnit.SECONDS);

        // 3. 最后抢票：15秒后放出
        executorService.schedule(() -> {
            System.out.println("\n=== 最后抢票开始放出 ===");
            scheduleAtFixedMonitoring("最后抢票", lastMinuteTickets, executorService);
        }, 15, TimeUnit.SECONDS);

        // 系统运行30秒后关闭
        executorService.schedule(() -> {
            System.out.println("\n票务系统关闭");
            executorService.shutdown();
        }, 30, TimeUnit.SECONDS);
    }

    /**
     * 票务监控人任务
     * @param ticketType 票类型
     * @param tickets  票计数器
     * @param schedule 调度器
     */
    private static void scheduleAtFixedMonitoring(String ticketType,AtomicInteger tickets, ScheduledExecutorService schedule){
         schedule.scheduleAtFixedRate(()->{
             int remaining = tickets.get();
             if (remaining>0){
                 int sold = (int)(Math.random()*5)+1;
                 int newRemaining = tickets.addAndGet(-sold);
                 if (newRemaining<0){
                     newRemaining = 0;
                     tickets.set(0);
                     sold+=newRemaining;//调整实际卖出的数量
                 }
                 System.out.printf("[%s] 卖出 %d 张，剩余 %d 张%n",
                         ticketType, sold, Math.max(newRemaining, 0));

                 if (newRemaining <= 0) {
                     System.out.printf("-- %s 已售罄 --%n", ticketType);
                     throw new RuntimeException("stop monitoring"); // 通过异常停止任务
                 }
             }
         },0,1, TimeUnit.SECONDS);
    }

}
