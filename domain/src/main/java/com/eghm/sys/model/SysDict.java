package com.eghm.sys.model;

import com.eghm.common.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDict extends BaseEntity {

    /** 字典中文名称 */
    private String title;

    /** 字典编码 */
    private String nid;

    /** 字典分类: 1: 系统字典 2: 业务字典 */
    private Integer dictType;

    /** 备注信息 */
    private String remark;

}
