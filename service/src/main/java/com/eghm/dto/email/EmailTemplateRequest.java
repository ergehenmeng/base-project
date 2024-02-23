package com.eghm.dto.email;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/12/15
 */
@Data
public class EmailTemplateRequest {

    @ApiModelProperty(value = "id主键", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "模板内容", required = true)
    @NotBlank(message = "模板内容不能为空")
    private String content;

    @ApiModelProperty("备注信息")
    private String remark;
}
