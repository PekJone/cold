package com.itheima.cold.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.cold.user.service.SysUserService;
import com.ithema.cold.common.user.dao.SysUserDao;
import com.ithema.cold.common.user.entity.SysUserEntity;
import org.springframework.stereotype.Service;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-04  17:21
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao,SysUserEntity> implements SysUserService {

    @Override
    public SysUserEntity queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }

    @Override
    public SysUserEntity queryByUserId(Long userId) {
        return baseMapper.queryByUserId(userId);
    }
}
