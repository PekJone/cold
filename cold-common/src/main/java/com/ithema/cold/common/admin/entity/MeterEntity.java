package com.ithema.cold.common.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 */
@Data
@TableName("cz_meter")
public class MeterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备主键
	 */
	@TableId
	private String id;
	/**
	 * 设备编码
	 */
	private String metercode;
	/**
	 * 设备名称
	 */
	private String metername;
	/**
	 * 主机Id
	 */
	private String hostid;
	/**
	 * 主机编码
	 */
	private String hostcode;
	/**
	 * 主机名称
	 */
	private String hostname;
	/**
	 * 仓库id
	 */
	private String houseid;
	/**
	 * 仓库编码
	 */
	private String housecode;
	/**
	 * 仓库名称
	 */
	private String housename;
	/**
	 * 公司id
	 */
	private String companyid;
	/**
	 * 公司名称
	 */
	private String companyname;
	/**
	 * 设备状态: 1-在用，0-停用，2-异常
	 */
	private String meterstatus;
	/**
	 * 温度上限
	 */
	private String maxtem;
	/**
	 * 温度下限
	 */
	private String mintem;
	/**
	 * 湿度上限
	 */
	private String maxhum;
	/**
	 * 湿度下限
	 */
	private String minhum;
	/**
	 * 采集间隔(秒)
	 */
	private Integer meterinterval;
	/**
	 * 备注信息
	 */
	private String remarks;
	/**
	 * 创建时间
	 */
	private Date createdtime;
	/**
	 * 修改时间
	 */
	private Date updatetime;

}
