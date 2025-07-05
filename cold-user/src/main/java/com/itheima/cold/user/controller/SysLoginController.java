package com.itheima.cold.user.controller;

import com.itheima.cold.user.service.SysUserService;
import com.itheima.cold.user.service.SysUserTokenService;
import com.ithema.cold.common.user.entity.SysLoginForm;
import com.ithema.cold.common.user.entity.SysUserEntity;
import com.ithema.cold.common.user.entity.SysUserTokenEntity;
import com.ithema.cold.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-04  17:19
 */
@Slf4j
@RestController
@RequestMapping("/sys/user")
public class SysLoginController {
    private SysUserService sysUserService;
    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService= sysUserService;
    }

    private SysUserTokenService sysUserTokenService;
    @Autowired
    public void setSysUserTokenService(SysUserTokenService sysUserTokenService) {
        this.sysUserTokenService = sysUserTokenService;
    }
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody SysLoginForm form) throws IOException {

        SysUserEntity user = sysUserService.queryByUserName(form.getUsername());
        if (user==null || user.getPassword().equals(new Sha256Hash(form.getPassword(),user.getSalt()).toHex())){
            return Result.error("账号密码不正确");
        }
        Result r = sysUserTokenService.createToken(user.getUserId());
        log.info("{}用户登录成功",user.getUsername());
        return r;

    }

    @PostMapping("/logout")
    public Result logout(@RequestHeader(value = "token")String token){
        SysUserTokenEntity entity = sysUserTokenService.queryByToken(token);
        SysUserEntity userEntity = null;
        if (entity!=null){
            userEntity = sysUserService.queryByUserId(entity.getUserId());
        }else{
            return Result.ok();
        }
        sysUserTokenService.logout(token);
        log.info("{}用户退出登录",userEntity.getUsername());
        return Result.ok();
    }

    /**
     * 用户信息
     */
    @GetMapping("/info")
    public Result userInfo() {
        SysUserEntity user = sysUserService.queryByUserName("admin");
        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getUsername());
        map.put("company", "传智播客");
        map.put("phone","");
        map.put("avatar","");
        map.put("username", user.getUsername());
        map.put("logintime", "");
        return Result.ok(map);
    }

}
