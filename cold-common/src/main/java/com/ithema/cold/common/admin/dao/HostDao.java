package com.ithema.cold.common.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ithema.cold.common.admin.entity.HostEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 主机表
 *
 */
@Mapper
public interface HostDao extends BaseMapper<HostEntity> {
	
}
