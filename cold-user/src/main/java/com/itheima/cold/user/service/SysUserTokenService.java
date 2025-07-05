package com.itheima.cold.user.service;

import com.ithema.cold.common.user.entity.SysUserTokenEntity;
import com.ithema.cold.common.utils.Result;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-04  17:21
 */
public interface SysUserTokenService {

    Result createToken(Long userId);

    void logout(String token);

    SysUserTokenEntity queryByToken(String token);
}
