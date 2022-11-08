package com.eghm.model.dto.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/26 16:17
 */
@Data
public class RoleAddRequest implements Serializable {

    private static final long serialVersionUID = 8213867116327534945L;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @ApiModelProperty(value = "角色类型编码", required = true)
    @NotBlank(message = "角色类型不能为空")
    private String roleType;

    @ApiModelProperty("备注信息")
    private String remark;

}
