package com.ithema.cold.common.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ithema.cold.common.user.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token
 *
 *
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    SysUserTokenEntity queryByToken(String token);

    void deleteByToken(String token);
}
