package com.ithema.cold.common.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-02  18:19
 */
@Data
@TableName("cz_company")
public class CompanyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 企业名称
     */
    private String company;
    /**
     * 企业简称
     */
    private String abbreviation;
    /**
     * 企业编号
     */
    private String companynumber;
    /**
     * 公司地址
     */
    private String companyaddress;
    /**
     * 公司电话
     */
    private String companyphone;

    /**
     * 质量管理员
     */
    private String managementname;

    /**
     * 质管员电话
     */
    private String managementphone;

    /**
     * 负责人姓名
     */
    private String leader;

    /**
     * 负责人电话
     */
    private String leaderphone;

    /**
     * 网站
     */
    private String webaddress;

    /**
     * 备注
     */
    private String message;

    /**
     * 企业类型
     */
    private String companytype;


    /**
     * 创建时间
     */
    private Date createdtime;
    /**
     * 修改时间
     */
    private Date updatetime;

}

