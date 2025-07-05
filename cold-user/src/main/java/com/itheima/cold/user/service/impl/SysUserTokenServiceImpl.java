package com.itheima.cold.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.cold.user.service.SysUserService;
import com.itheima.cold.user.service.SysUserTokenService;
import com.ithema.cold.common.user.dao.SysUserTokenDao;
import com.ithema.cold.common.user.entity.SysUserTokenEntity;
import com.ithema.cold.common.utils.Result;
import com.ithema.cold.common.utils.TokenGenerator;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-04  17:22
 */
@Service
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
    public static final int EXPIRE = 3600*12;
    @Override
    public Result createToken(Long userId) {
        String token = TokenGenerator.generateValue();
        Date date = new Date();
        Date expireTime = new Date(date.getTime()+EXPIRE*1000);
        SysUserTokenEntity userTokenEntity = this.getById(userId);
        if(userTokenEntity==null){
            userTokenEntity = new SysUserTokenEntity();
            userTokenEntity.setUserId(userId);
            userTokenEntity.setToken(token);
            userTokenEntity.setExpireTime(expireTime);
            userTokenEntity.setUpdateTime(date);
            this.save(userTokenEntity);
        }else{
            userTokenEntity.setToken(token);
            userTokenEntity.setExpireTime(expireTime);
            userTokenEntity.setUpdateTime(date);
            this.updateById(userTokenEntity);
        }
        Result r= Result.ok().put("token",token).put("expire",expireTime);
        return r;
    }

    @Override
    public void logout(String token) {
        this.getBaseMapper().deleteByToken(token);
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return this.getBaseMapper().queryByToken(token);
    }
}
