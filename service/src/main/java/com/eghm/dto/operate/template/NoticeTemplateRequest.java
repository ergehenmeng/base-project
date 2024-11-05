package com.eghm.dto.operate.template;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/12/15
 */

@Data
public class NoticeTemplateRequest {

    @ApiModelProperty(value = "id主键", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "通知内容", required = true)
    @NotBlank(message = "通知内容不能为空")
    private String content;

    @ApiModelProperty(value = "备注信息")
    private String remark;
}
