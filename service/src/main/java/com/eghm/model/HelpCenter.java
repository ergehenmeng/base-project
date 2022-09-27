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
@TableName("help_center")
public class HelpCenter extends BaseEntity {

    @ApiModelProperty("帮助分类取sys_dict表中help_classify字段")
    private Byte classify;

    @ApiModelProperty("状态 0:不显示 1:显示")
    private Byte state;

    @ApiModelProperty("问")
    private String ask;

    @ApiModelProperty("答")
    private String answer;

    @ApiModelProperty("排序(小<->大)")
    private Byte sort;

}