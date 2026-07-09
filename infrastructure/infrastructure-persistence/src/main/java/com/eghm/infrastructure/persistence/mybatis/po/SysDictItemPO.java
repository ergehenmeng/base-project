package com.eghm.infrastructure.persistence.mybatis.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_dict_item")
@EqualsAndHashCode(callSuper = true)
public class SysDictItemPO extends BaseEntityPO {

    /** 字典编码 */
    private String nid;

    /** 数据字典隐藏值 */
    private Integer hiddenValue;

    /** 显示值 */
    private String showValue;
}
