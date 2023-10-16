package com.eghm.vo.help;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/11/12
 */
@Data
@ApiModel
public class HelpCenterVO {

    @ApiModelProperty("帮助分类")
    private Integer classify;

    @ApiModelProperty("问题标题")
    private String ask;

    @ApiModelProperty("回答")
    private String answer;
}
