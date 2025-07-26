package com.concert.controller;

import com.concert.dto.UserRequest;
import com.concert.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-25  16:13
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private QueueService queueService;

    @PostMapping("/request")
    public String requestTicket(
            @RequestParam int userId,
            @RequestParam String ticketType,
            @RequestParam int quantity,
            @RequestParam int priority) {

        UserRequest request = new UserRequest(userId, ticketType, quantity, priority);
        queueService.addRequest(request);

        return String.format("用户%d的%s购票请求(优先级%d)已加入队列",
                userId, ticketType, priority);
    }
}
