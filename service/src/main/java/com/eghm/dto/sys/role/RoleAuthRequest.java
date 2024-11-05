package com.eghm.dto.sys.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛 2022/6/25 16:30
 */
@Data
public class RoleAuthRequest {

    @ApiModelProperty(value = "角色id", required = true)
    @NotNull(message = "请选择要授权的角色")
    private Long roleId;

    @ApiModelProperty("菜单ids")
    private List<Long> menuIds;

}
