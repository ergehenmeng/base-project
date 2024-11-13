package com.eghm.vo.operate.help;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 殿小二
 * @since 2020/11/12
 */
@Data
public class HelpCenterVO {

    @ApiModelProperty("帮助分类")
    private Integer helpType;

    @ApiModelProperty("问题标题")
    private String ask;

    @ApiModelProperty("回答")
    private String answer;
}
