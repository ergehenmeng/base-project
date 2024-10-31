package com.eghm.dto.role;

import com.eghm.annotation.Assign;
import com.eghm.enums.RoleType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2018/11/26 16:17
 */
@Data
public class RoleAddRequest {

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 210, message = "角色名称最大10字符")
    private String roleName;

    @ApiModelProperty("备注信息")
    @Size(max = 100, message = "备注信息最大100字符")
    private String remark;

    @ApiModelProperty(value = "角色类型", hidden= true)
    @Assign
    private RoleType roleType;

    @ApiModelProperty(value = "商户id", hidden = true)
    @Assign
    private Long merchantId;
}
