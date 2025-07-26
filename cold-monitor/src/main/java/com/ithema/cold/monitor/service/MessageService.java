package com.ithema.cold.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ithema.cold.common.netty.MessageEntity;

import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-26  11:20
 */
public interface MessageService extends IService<MessageEntity> {
    IPage queryMessageRealTime(Map<String,Object> params);
}
