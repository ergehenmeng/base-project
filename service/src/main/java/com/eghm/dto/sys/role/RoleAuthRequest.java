package com.eghm.dto.sys.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛 2022/6/25 16:30
 */
@Data
public class RoleAuthRequest {

    @Schema(description = "角色id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择要授权的角色")
    private Long roleId;

    @Schema(description = "菜单ids")
    private List<Long> menuIds;

}
