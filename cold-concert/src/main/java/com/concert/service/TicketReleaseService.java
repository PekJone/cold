package com.concert.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  15:54
 */
@Service
public class TicketReleaseService {
    @Autowired
    private ScheduledExecutorService scheduledExecutorService;
    @Autowired
    private TicketService ticketService;

    public void scheduleTicketReleases(){
        scheduledExecutorService.schedule(()->{
           ticketService.releaseTickets("presale");
           startTicketMonitoring("presale");
        },5, TimeUnit.SECONDS);
        // 正式票：10秒后放出
        scheduledExecutorService.schedule(() -> {
            ticketService.releaseTickets("regular");
            startTicketMonitoring("regular");
        }, 10, TimeUnit.SECONDS);

        // 最后抢票：15秒后放出
        scheduledExecutorService.schedule(() -> {
            ticketService.releaseTickets("lastMinute");
            startTicketMonitoring("lastMinute");
        }, 15, TimeUnit.SECONDS);
    }


    protected void startTicketMonitoring(String ticketType) {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            int remaining = ticketService.checkRemainingTickets(ticketType);
            if (remaining <= 0) {
                throw new RuntimeException(ticketType + " sold out");
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

}
