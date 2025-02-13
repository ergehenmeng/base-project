package com.eghm.dto.operate.template;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/12/15
 */

@Data
public class NoticeTemplateRequest {

    @Schema(description = "id主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "标题不能为空")
    private String title;

    @Schema(description = "通知内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "通知内容不能为空")
    private String content;

    @Schema(description = "备注信息")
    private String remark;
}
