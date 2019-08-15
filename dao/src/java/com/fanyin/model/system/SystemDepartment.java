package com.fanyin.model.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门信息表
 * @author 二哥很猛
 */
@Data
public class SystemDepartment implements Serializable {

    private static final long serialVersionUID = 3509025826333039284L;

    /**
     * 主键<br>
     * 表 : system_department<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 父级编号<br>
     * 表 : system_department<br>
     * 对应字段 : parent_code<br>
     */
    private String parentCode;

    /**
     * 部门编号<br>
     * 表 : system_department<br>
     * 对应字段 : code<br>
     */
    private String code;

    /**
     * 部门名称<br>
     * 表 : system_department<br>
     * 对应字段 : name<br>
     */
    private String title;

    /**
     * 添加时间<br>
     * 表 : system_department<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : system_department<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 删除状态 0:未删除 1:已删除<br>
     * 表 : system_department<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作人id
     */
    private Integer operatorId;

}