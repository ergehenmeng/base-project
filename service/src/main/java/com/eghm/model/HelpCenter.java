package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.handler.mysql.LikeTypeHandler;
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
@TableName("help_center")
public class HelpCenter extends BaseEntity {

    @ApiModelProperty("帮助分类取sys_dict表中help_classify字段")
    private Integer classify;

    @ApiModelProperty("状态 0:不显示 1:显示")
    private Integer state;

    @ApiModelProperty("问")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String ask;

    @ApiModelProperty("答")
    private String answer;

    @ApiModelProperty("排序(小<->大)")
    private Integer sort;

}