package com.ithema.cold.admin.controller;

import com.ithema.cold.common.admin.entity.OperationLog;
import com.ithema.cold.common.utils.Result;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-13  9:16
 */
@RestController
@RequestMapping("/admin/api")
public class AdminController {
    @RequestMapping("/total")
    public Result getTotal(@RequestParam Map<String, Object> params){
        Map<String, Object> map = new HashMap<>();
        map.put("equipmentTotal", "16");
        map.put("organizationTotal", "5");
        map.put("warningTotal","32342");
        map.put("meterTotal","16");
        map.put("accuracy", "57");
        map.put("unaccomplished", "32123");
        map.put("finishNumber", "2312");
        return Result.ok(map);
    }
    @PostMapping("/operation/getLog")
    public Result getOperationLog(@RequestParam Map<String,Object> params){

        List<OperationLog> operationLogs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            OperationLog log = new OperationLog();
            log.setId(UUID.randomUUID().toString().toUpperCase().replace("-",""));
            log.setName("admin");
            log.setMsg("开风机");
            log.setStorehouse("昌平一号库房");
            log.setOperationTime("2019-08-20 10:21:00");
            operationLogs.add(log);
        }
        Map<String,Object> map = new HashMap<>();

        map.put("page",1);
        map.put("total",9);
        map.put("items",operationLogs);
        return Result.ok(map);
    }

}
