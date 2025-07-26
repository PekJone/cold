package com.concert.service;

import com.concert.dto.RequestResult;
import com.concert.dto.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  16:06
 */
@Service
public class QueueService {
    @Autowired
    private PriorityBlockingQueue<UserRequest> userRequests;
    @Autowired
    private TicketService ticketService;

    public void addRequest(UserRequest request){
        userRequests.offer(request);
    }

    public void startProcessing(){
        new Thread(()->{
            while (true){
                try{
                    UserRequest request = userRequests.take();
                    RequestResult result = ticketService.purchaseTickets(
                            request.getTicketType(),
                            request.getUserId(),
                            request.getQuantity()
                    );
                    System.out.printf("[用户%d] %s%n",
                            result.getUserId(), result.getMessage());
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }
}
