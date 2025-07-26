package com.concert.service;

import com.concert.dto.RequestResult;
import com.concert.model.TicketPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  15:36
 */
@Service
public class TicketService {
    @Value("${ticket.presale.count:100}")
    private int presaleCount;
    @Value("${ticket.regular.count:200}")
    private int regularCount;
    @Value("${ticket.lastMinute.count:200}")
    private int lastMinuteCount;

    private final Map<String,TicketPool> ticketPools = new HashMap<>();

    @PostConstruct
    public void init(){
        ticketPools.put("presale",new TicketPool("预售票",presaleCount));
        ticketPools.put("presale",new TicketPool("正式票",regularCount));
        ticketPools.put("presale",new TicketPool("最后抢票",lastMinuteCount));
    }

    public void releaseTickets(String ticketType){
        TicketPool pool = ticketPools.get(ticketType);
        if (pool!=null){
            System.out.println("\n[系统] " + pool.getPoolName() + "已放出 (" + pool.getTotalTickets() + "张)");
        }
    }

    public int checkRemainingTickets(String ticketType){
        TicketPool pool = ticketPools.get(ticketType);
        return pool!=null?pool.getRemainingTickets():0;
    }

    public RequestResult purchaseTickets(String ticketType,int userId,int quantity){
        TicketPool pool = ticketPools.get(ticketType);
        if(pool==null){
            return new RequestResult(userId,false,"无效票类型："+ticketType);
        }
        if (pool.purchaseTickets(quantity)){
            return new RequestResult(userId,true,"成功购买"+quantity+"张"+pool.getPoolName());
        }else {
            return new RequestResult(userId, false,
                    pool.getPoolName() + "余票不足 (" + quantity + "张需要，剩余" + pool.getRemainingTickets() + "张)");
        }
    }
}
