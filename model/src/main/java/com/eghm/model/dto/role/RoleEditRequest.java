package com.eghm.model.dto.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 编辑角色信息
 * @author 二哥很猛
 * @date 2018/11/26 16:08
 */
@Data
public class RoleEditRequest implements Serializable {

    private static final long serialVersionUID = -4613884225645951474L;

    /**
     * 主键
     */
    @ApiModelProperty("主键id")
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    @NotNull(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色类型
     */
    @ApiModelProperty("角色类型编码")
    @NotNull(message = "角色类型不能为空")
    private String roleType;

    /**
     * 备注信息
     */
    @ApiModelProperty("备注信息")
    private String remark;

}
