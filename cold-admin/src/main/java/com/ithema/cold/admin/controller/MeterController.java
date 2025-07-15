package com.ithema.cold.admin.controller;

import com.ithema.cold.admin.service.MeterService;
import com.ithema.cold.common.admin.entity.MeterEntity;
import com.ithema.cold.common.exception.MyException;
import com.ithema.cold.common.utils.PageUtils;
import com.ithema.cold.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-13  9:17
 */
@RestController
@RequestMapping("/admin/meter")
public class MeterController {
    private MeterService meterService;

    private RedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setMeterService(MeterService meterService) {
        this.meterService = meterService;
    }

    @PostMapping("/list")
    public Result list(@RequestBody Map<String,Object> params){
        PageUtils pageUtils = meterService.queryPage(params);
        return Result.ok(pageUtils.getPageMap());
    }


    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public Result saveMeter(@RequestBody MeterEntity meterEntity){
        meterEntity.setId(UUID.randomUUID().toString());
        if(meterEntity.getId() == null){
            throw new MyException("仪表盘编码不能为空");
        }
        Date date = new Date();
        meterEntity.setCreatedtime(date);
        meterEntity.setUpdatetime(date);
        meterService.saveOrUpdate(meterEntity);
        redisTemplate.opsForValue().set("meter:"+meterEntity.getMetercode(),meterEntity,1, TimeUnit.HOURS);
        return Result.ok().put("code",meterEntity.getMetercode());
    }

    @PostMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public Result update(@RequestBody MeterEntity meter){
        meterService.updateById(meter);
        redisTemplate.delete("meter:"+meter.getMetercode());
        redisTemplate.opsForValue().set("meter:"+meter.getMetercode(),meter);
        return Result.ok();
    }

    @RequestMapping("/delete")
    public Result delete(String id){
          meterService.removeById(id);
          redisTemplate.delete("meter:"+meterService.getById(id).getMetercode());
          return Result.ok();
    }

}
