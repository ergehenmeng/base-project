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

    @ApiModelProperty("id主键")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty("模板内容")
    @NotBlank(message = "模板内容不能为空")
    private String content;

    @ApiModelProperty("备注信息")
    private String remark;
}
