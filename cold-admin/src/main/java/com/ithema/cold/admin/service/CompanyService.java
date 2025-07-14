package com.ithema.cold.admin.service;

import com.ithema.cold.common.admin.entity.CompanyEntity;
import com.ithema.cold.common.utils.PageUtils;

import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-13  9:21
 */
public interface CompanyService {
     

    PageUtils queryPage(Map<String, Object> param);

    CompanyEntity getById(String id);

    boolean save(CompanyEntity companyEntity);

    boolean updateById(CompanyEntity companyEntity);

    int removeById(String id);
}
