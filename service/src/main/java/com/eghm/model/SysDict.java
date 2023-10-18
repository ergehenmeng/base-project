package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.handler.mysql.LikeTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_dict")
@EqualsAndHashCode(callSuper = true)
public class SysDict extends BaseEntity {

    @ApiModelProperty("字典中文名称")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String title;

    @ApiModelProperty("数据字典nid(英文名称)")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String nid;

    @ApiModelProperty("数据字典隐藏值")
    private Integer hiddenValue;

    @ApiModelProperty("显示值")
    private String showValue;

    @ApiModelProperty("锁定状态(禁止编辑):0:未锁定 1:锁定")
    private Boolean locked;

    @ApiModelProperty("备注信息")
    private String remark;

}