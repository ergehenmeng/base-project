package com.eghm.dto.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 编辑角色信息
 *
 * @author 二哥很猛
 * @since 2018/11/26 16:08
 */
@Data
public class RoleEditRequest {

    @ApiModelProperty(value = "主键id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 210, message = "角色名称最大10字符")
    private String roleName;

    @ApiModelProperty("备注信息")
    @Size(max = 100, message = "备注信息最大100字符")
    private String remark;
}
