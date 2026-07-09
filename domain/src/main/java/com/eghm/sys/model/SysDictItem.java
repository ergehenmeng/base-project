package com.eghm.sys.model;

import com.eghm.model.BaseEntity;

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

    public void initialize(String nid, Integer hiddenValue, String showValue) {
        this.nid = nid;
        this.hiddenValue = hiddenValue;
        this.showValue = showValue;
    }

    public void changeShowValue(String showValue) {
        this.showValue = showValue;
    }

    public void changeHiddenValue(Integer hiddenValue) {
        this.hiddenValue = hiddenValue;
    }
}
