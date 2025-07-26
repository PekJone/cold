package com.ithema.cold.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ithema.cold.common.netty.MessageEntity;
import com.ithema.cold.common.utils.Result;
import com.ithema.cold.monitor.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-26  11:19
 */
@RestController
@RequestMapping("device/monitor")
public class MessageController {
    private MessageService messageService;
    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping("/list")
    public Result deviceList(@RequestParam Map<String,Object> params){
        List<MessageEntity> list = messageService.queryMessageRealTime(params).getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("items",list);
        return Result.ok(map);
    }

    @RequestMapping("/realTime")
    public Result realTime(@RequestParam Map<String,Object> params){
        IPage<MessageEntity> list = messageService.queryMessageRealTime(params);
        Map<String,Object> map = new HashMap<>();
        map.put("items",list.getRecords());
        map.put("total",list.getTotal());
        map.put("page",list.getPages());
        return Result.ok(map);
    }
}
