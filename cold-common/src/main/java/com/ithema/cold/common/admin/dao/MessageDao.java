package com.ithema.cold.common.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ithema.cold.common.netty.MessageEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageDao extends BaseMapper<MessageEntity> {
}
