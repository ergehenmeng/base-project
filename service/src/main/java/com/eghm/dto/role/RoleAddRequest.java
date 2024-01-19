package com.eghm.dto.role;

import com.eghm.enums.ref.RoleType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2018/11/26 16:17
 */
@Data
public class RoleAddRequest {

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("角色类型")
    private RoleType roleType;

    @ApiModelProperty(value = "商户id", hidden = true)
    private Long merchantId;
}
