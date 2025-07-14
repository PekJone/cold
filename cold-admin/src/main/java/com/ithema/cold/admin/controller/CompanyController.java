package com.ithema.cold.admin.controller;

import com.ithema.cold.admin.service.CompanyService;
import com.ithema.cold.common.admin.entity.CompanyEntity;
import com.ithema.cold.common.utils.PageUtils;
import com.ithema.cold.common.utils.Result;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-13  9:17
 */
@RestController
@RequestMapping("/admin/company")
@Slf4j
public class CompanyController {
    private CompanyService companyService ;
    @Autowired
    public CompanyService setCompanyService(CompanyService companyService) {
        return this.companyService=companyService;
    }
    @PostMapping("/list")
    public Result getList(@RequestBody Map<String,Object> param){
        log.info("param:{}",param);
        PageUtils page = companyService.queryPage(param);
        return Result.ok(page.getPageMap());
    }

    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") String id){

        CompanyEntity companyEntity = companyService.getById(id);
        return Result.ok().put("company",companyEntity);

    }
    @PostMapping("/save")
    public Result save(@RequestBody CompanyEntity companyEntity){
        String id = UUID.randomUUID().toString();
        companyEntity.setId(id);
        Date date = new Date();
        companyEntity.setCreatedtime(date);
        companyEntity.setUpdatetime(date);
        companyService.save(companyEntity);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody CompanyEntity companyEntity){
        companyService.updateById(companyEntity);
        return Result.ok();
    }

    @RequestMapping("/delete")
    public Result delete(String id){
         companyService.removeById(id);
         return Result.ok();
    }

}
