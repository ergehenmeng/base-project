package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 系统参数表
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SysConfig extends BaseEntity {

    /**
     * 参数标示符<br>
     * 表 : sys_config<br>
     * 对应字段 : nid<br>
     */
    private String nid;

    /**
     * 参数名称<br>
     * 表 : sys_config<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 参数值<br>
     * 表 : sys_config<br>
     * 对应字段 : content<br>
     */
    private String content;

    /**
     * 备注信息<br>
     * 表 : sys_config<br>
     * 对应字段 : remark<br>
     */
    private String remark;

    /**
     * 参数类型,见sys_dict表nid=config_classify<br>
     * 表 : sys_config<br>
     * 对应字段 : classify<br>
     */
    private Byte classify;

    /**
     * 锁定状态(禁止编辑) 0:未锁定,1:锁定<br>
     * 表 : sys_config<br>
     * 对应字段 : locked<br>
     */
    private Boolean locked;

    /**
     * 添加时间<br>
     * 表 : sys_config<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : sys_config<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 参数类型 数据字典名称
     */
    private String classifyName;
}