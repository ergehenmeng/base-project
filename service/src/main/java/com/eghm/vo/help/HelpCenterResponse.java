package com.eghm.vo.help;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 殿小二
 * @since 2020/11/12
 */
@Data
public class HelpCenterResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("帮助分类")
    private Integer helpType;

    @ApiModelProperty("问题标题")
    private String ask;

    @ApiModelProperty("状态 0:不显示 1:显示")
    private Integer state;

    @ApiModelProperty("回答(html)")
    private String answer;

}
