package com.eghm.model.dto.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛 2022/6/25 16:30
 */
@Data
public class RoleAuthRequest {

    @ApiModelProperty("角色id")
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @ApiModelProperty("菜单id,逗号分割")
    private String menuIds;

}
