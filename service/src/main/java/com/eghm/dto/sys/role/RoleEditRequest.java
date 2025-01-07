package com.eghm.dto.sys.role;

import com.eghm.annotation.Assign;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 编辑角色信息
 *
 * @author 二哥很猛
 * @since 2018/11/26 16:08
 */
@Data
public class RoleEditRequest {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 210, message = "角色名称最大10字符")
    private String roleName;

    @Schema(description = "备注信息")
    @Size(max = 100, message = "备注信息最大100字符")
    private String remark;

    @Schema(description = "商户id", hidden = true)
    @Assign
    private Long merchantId;
}
