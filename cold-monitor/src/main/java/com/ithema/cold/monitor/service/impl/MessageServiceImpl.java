package com.ithema.cold.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ithema.cold.common.admin.dao.MessageDao;
import com.ithema.cold.common.netty.MessageEntity;
import com.ithema.cold.monitor.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-26  11:29
 */
@Service("messageService")
public class MessageServiceImpl  extends ServiceImpl<MessageDao, MessageEntity> implements MessageService {
    @Override
    public IPage queryMessageRealTime(Map<String, Object> params) {
        QueryWrapper   queryWrapper= getMessageCondition(params);
        int page = Integer.valueOf(params.get("page").toString());
        int size = Integer.valueOf(params.get("pageSize").toString());
        return this.page(new Page<MessageEntity>(page,size),queryWrapper);
    }

    private QueryWrapper getMessageCondition(Map<String, Object> params) {
        QueryWrapper<MessageEntity> queryWrapper = new QueryWrapper<>();
        String companyId = params.get("companyId")==null?null:params.get("companyId").toString().trim();
        if (companyId!=null){
            queryWrapper.eq("companyId",companyId);
        }
        return queryWrapper;
    }
}
