package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("sys_dict")
public class SysDict extends BaseEntity {

    @ApiModelProperty("字典中文名称")
    private String title;

    @ApiModelProperty("数据字典nid(英文名称)")
    private String nid;

    @ApiModelProperty("数据字典隐藏值")
    private Byte hiddenValue;

    @ApiModelProperty("显示值")
    private String showValue;

    @ApiModelProperty("锁定状态(禁止编辑):0:未锁定 1:锁定")
    private Boolean locked;

    @ApiModelProperty("备注信息")
    private String remark;

}