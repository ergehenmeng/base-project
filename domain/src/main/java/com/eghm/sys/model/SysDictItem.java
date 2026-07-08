package com.eghm.sys.model;

import com.eghm.common.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictItem extends BaseEntity {

    /** 字典编码 */
    private String nid;

    /** 数据字典隐藏值 */
    private Integer hiddenValue;

    /** 显示值 */
    private String showValue;
}
