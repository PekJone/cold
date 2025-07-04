package com.ithema.cold.common.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import lombok.Data;

/**
 * 仓库表
 *
 */
@Data
@TableName("cz_warehouse")
public class WarehouseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 仓库编码
	 */
	private String housecode;
	/**
	 * 仓库名称
	 */
	private String housename;
	/**
	 * 仓库地址
	 */
	private String houseaddress;
	/**
	 * 仓库类型， 1：冷库，2：恒温库
	 *
	 */
	private String housetype;
	/**
	 * 所属公司Id
	 */
	private String companyid;
	/**
	 * 公司名称
	 */
	private String companyname;
	/**
	 * 负责人
	 */
	private String principalname;
	/**
	 * 负责人电话
	 */
	private String principaltel;
	/**
	 * 经度
	 */
	private BigDecimal longitude;
	/**
	 * 纬度
	 */
	private BigDecimal latitude;

	/**
	 * 库房面积
	 */
	private Double areasize;

	/**
	 * 状态：1-正常,0-空库
	 */
	private String housestatus;
	/**
	 * 创建时间
	 */
	private Date createdtime;
	/**
	 * 修改时间
	 */
	private Date updatetime;

	@TableField(exist = false)
	private Map<String, String> markerPoint;
}
