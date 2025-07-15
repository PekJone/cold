package com.ithema.cold.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ithema.cold.common.admin.entity.MeterEntity;
import com.ithema.cold.common.utils.PageUtils;

import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-13  9:21
 */
public interface MeterService extends IService<MeterEntity> {
    PageUtils queryPage(Map<String, Object> params);
}
