package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_dict_item")
@EqualsAndHashCode(callSuper = true)
public class SysDictItem extends BaseEntity {

    @ApiModelProperty("字典编码")
    private String nid;

    @ApiModelProperty("数据字典隐藏值")
    private Integer hiddenValue;

    @ApiModelProperty("显示值")
    private String showValue;
}