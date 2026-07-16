package com.eghm.platform.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 编辑角色信息
 *
 * @author 二哥很猛
 * @since 2018/11/26 16:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleEditRequest extends RoleAddRequest {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;
}
