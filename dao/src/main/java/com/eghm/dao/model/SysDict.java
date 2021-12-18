package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SysDict extends BaseEntity {

    /**
     * 字典中文名称<br>
     * 表 : sys_dict<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 数据字典nid(英文名称)<br>
     * 表 : sys_dict<br>
     * 对应字段 : nid<br>
     */
    private String nid;

    /**
     * 数据字典隐藏值<br>
     * 表 : sys_dict<br>
     * 对应字段 : hidden_value<br>
     */
    private Byte hiddenValue;

    /**
     * 显示值<br>
     * 表 : sys_dict<br>
     * 对应字段 : show_value<br>
     */
    private String showValue;

    /**
     * 删除状态 0:正常,1:已删除<br>
     * 表 : sys_dict<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 锁定状态(禁止编辑):0:未锁定 1:锁定<br>
     * 表 : sys_dict<br>
     * 对应字段 : locked<br>
     */
    private Boolean locked;

    /**
     * 备注信息<br>
     * 表 : sys_dict<br>
     * 对应字段 : remark<br>
     */
    private String remark;

}