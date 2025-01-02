package com.eghm.dto.sys.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 更新系统配置信息的请求参数类
 *
 * @author 二哥很猛
 * @since 2018/1/12 17:37
 */
@Data
public class ConfigEditRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "参数名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "参数名称不能为空")
    private String title;

    @Schema(description = "参数值", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "参数值不能为空")
    private String content;

    @Schema(description = "备注信息")
    private String remark;

}
