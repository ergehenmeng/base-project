package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 部门信息表
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SysDept extends BaseEntity {

    /**
     * 父级编号<br>
     * 表 : sys_dept<br>
     * 对应字段 : parent_code<br>
     */
    private String parentCode;

    /**
     * 部门编号<br>
     * 表 : sys_dept<br>
     * 对应字段 : code<br>
     */
    private String code;

    /**
     * 部门名称<br>
     * 表 : sys_dept<br>
     * 对应字段 : name<br>
     */
    private String title;

    /**
     * 删除状态 0:未删除 1:已删除<br>
     * 表 : sys_dept<br>
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
    private Long operatorId;

}