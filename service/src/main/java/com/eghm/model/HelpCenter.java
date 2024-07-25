package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("help_center")
@EqualsAndHashCode(callSuper = true)
public class HelpCenter extends BaseEntity {

    @ApiModelProperty("帮助分类取sys_dict表中help_type字段")
    private Integer helpType;

    @ApiModelProperty("状态 0:不显示 1:显示")
    private Integer state;

    @ApiModelProperty("问")
    private String ask;

    @ApiModelProperty("答")
    private String answer;

    @ApiModelProperty("排序(小<->大)")
    private Integer sort;

}