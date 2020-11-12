package com.eghm.model.vo.help;

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

    /**
     * 帮助分类
     */
    @ApiModelProperty("帮助分类")
    private Byte classify;

    /**
     * 问题标题
     */
    @ApiModelProperty("问题标题")
    private String ask;

    /**
     * 回答
     */
    @ApiModelProperty("回答")
    private String answer;
}
