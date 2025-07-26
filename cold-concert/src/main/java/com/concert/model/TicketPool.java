package com.concert.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  10:48
 */
public class TicketPool {
    private final String poolName;
    private final AtomicInteger remainingTickets;
    private final int totalTickets;

    public TicketPool(String poolName,int totalTickets){
        this.poolName =poolName;
        this.totalTickets = totalTickets;
        this.remainingTickets = new AtomicInteger(totalTickets);
    }

    public boolean purchaseTickets(int quantity){
        int current ;
        do{
            current = remainingTickets.get();
            if (current<quantity){
                return false;
            }
        }while (!remainingTickets.compareAndSet(current,current-quantity));
        return true;
    }

    public String getPoolName() {
        return poolName;
    }

    public int getRemainingTickets() {
        return remainingTickets.get();
    }

    public int getTotalTickets() {
        return totalTickets;
    }
}
