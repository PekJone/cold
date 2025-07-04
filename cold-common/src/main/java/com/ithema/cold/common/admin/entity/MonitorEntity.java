package com.ithema.cold.common.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("cz_monitor")
public class MonitorEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;


    /**
     * 监控人员
     */
    private String username;

    /**
     * 人员电话
     */
    private String userphone;

    /**
     * 预警类型, 0:温湿度，1：温度，2：湿度
     */
    private String monitorstate;

    /**
     * 监控主机id
     */
    private String hostid;

    /**
     * 监控主机编码
     */
    private String hostcode;

    /**
     * 监控主机名称
     */
    private String hostname;
}
