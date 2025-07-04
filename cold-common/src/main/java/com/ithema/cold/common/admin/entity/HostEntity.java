package com.ithema.cold.common.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 主机表
 *
 */
@Data
@TableName("cz_host")
public class HostEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
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
	 * 主机状态：1-正常，0-停用
	 */
	private String hoststatus;
	/**
	 * 主机型号
	 */
	private String hostmodel;
	/**
	 * sim卡号
	 */
	private String simcode;
	/**
	 * 创建时间
	 */
	private Date createdtime;
	/**
	 * 修改时间
	 */
	private Date updatetime;

}
