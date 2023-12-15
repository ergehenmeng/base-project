package com.eghm.dto.template;

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

    @ApiModelProperty("id主键")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty("通知内容")
    @NotBlank(message = "通知内容不能为空")
    private String content;
}
