package com.eghm.dto.sys.role;

import com.eghm.annotation.Assign;
import com.eghm.enums.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2018/11/26 16:17
 */
@Data
public class RoleAddRequest {

    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 210, message = "角色名称最大10字符")
    private String roleName;

    @Schema(description = "备注信息")
    @Size(max = 100, message = "备注信息最大100字符")
    private String remark;

    @Schema(description = "角色类型", hidden= true)
    @Assign
    private RoleType roleType;

    @Schema(description = "商户id", hidden = true)
    @Assign
    private Long merchantId;
}
