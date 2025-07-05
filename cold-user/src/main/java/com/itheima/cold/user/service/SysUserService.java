package com.itheima.cold.user.service;

import com.ithema.cold.common.user.entity.SysUserEntity;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-04  17:20
 */
public interface SysUserService {
    SysUserEntity queryByUserName(String username);

    SysUserEntity queryByUserId(Long userId);
}
