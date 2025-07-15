package com.ithema.cold.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ithema.cold.admin.service.MeterService;
import com.ithema.cold.common.admin.dao.MeterDao;
import com.ithema.cold.common.admin.entity.CompanyEntity;
import com.ithema.cold.common.admin.entity.MeterEntity;
import com.ithema.cold.common.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-13  9:26
 */
@Service
@Slf4j
public class MeterServiceImpl extends ServiceImpl<MeterDao, MeterEntity> implements MeterService {

    @Override
    public PageUtils queryPage(Map<String, Object> param) {
        int current = param.get("page")==null?1:Integer.valueOf(param.get("page").toString());
        int size = param.get("pageSize")==null?10:Integer.valueOf(param.get("pageSize").toString());

        Page<MeterEntity> page = new Page<>(current,size);
        String houseName = param.get("housename").toString();
        log.info("page:{},pagesize:{},houseName:{}",current,size,houseName);
        QueryWrapper<MeterEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(MeterEntity::getHousename,houseName)
                .orderByAsc(MeterEntity::getMetername);
        IPage<MeterEntity> result = this.page(page,wrapper);
        return new PageUtils(result);
    }
}
