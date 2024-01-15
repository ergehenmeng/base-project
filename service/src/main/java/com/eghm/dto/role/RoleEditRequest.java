package com.eghm.dto.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 编辑角色信息
 * @author 二哥很猛
 * @date 2018/11/26 16:08
 */
@Data
public class RoleEditRequest {

    @ApiModelProperty(value = "主键id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty(value = "商户id", hidden = true)
    private Long merchantId;
}
