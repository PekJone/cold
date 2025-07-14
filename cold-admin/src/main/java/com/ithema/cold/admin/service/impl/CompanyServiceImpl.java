package com.ithema.cold.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ithema.cold.admin.service.CompanyService;
import com.ithema.cold.common.admin.dao.CompanyDao;
import com.ithema.cold.common.admin.entity.CompanyEntity;
import com.ithema.cold.common.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-13  9:23
 */
@Service
@Slf4j
public class CompanyServiceImpl extends ServiceImpl<CompanyDao, CompanyEntity> implements CompanyService {
    private CompanyDao companyDao;
    @Autowired
    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> param) {
        int current = param.get("page")==null?1:Integer.valueOf(param.get("page").toString());
        int size = param.get("pageSize")==null?10:Integer.valueOf(param.get("pageSize").toString());

        Page<CompanyEntity> page = new Page<>(current,size);
        log.info("page:{},pagesize:{}",current,size);
        String company = param.get("company").toString();
        QueryWrapper<CompanyEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(CompanyEntity::getCompany,company);
        IPage<CompanyEntity> result = this.page(page,wrapper);
        return new PageUtils(result);
    }

    @Override
    public CompanyEntity getById(String id) {
        QueryWrapper<CompanyEntity> wrapper = new QueryWrapper<>();
//        方式三
//        wrapper.lambda().eq(CompanyEntity::getId,id);
//        方式二
//        wrapper.eq("id",id);
        CompanyEntity companyEntity = companyDao.selectOne(wrapper);
//        方式一
//        CompanyEntity companyEntity = companyDao.selectById(id);

        return companyEntity;
    }

    @Override
    public boolean save(CompanyEntity entity) {
        return super.save(entity);
    }

    @Override
    public int removeById(String id) {
       return companyDao.deleteById(id);
    }
}
