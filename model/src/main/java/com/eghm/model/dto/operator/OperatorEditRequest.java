package com.eghm.model.dto.operator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/30 16:37
 */
@Data
public class OperatorEditRequest implements Serializable {
    private static final long serialVersionUID = 2695362882925858038L;

    @ApiModelProperty(value = "id主键", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "用户名称", required = true)
    @NotNull(message = "用户名称不能为空")
    private String operatorName;

    @ApiModelProperty(value = "手机号", required = true)
    @NotNull(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "所属部门编号", required = true)
    @NotNull(message = "所属部门不能为空")
    private String deptCode;

    @ApiModelProperty(value = "角色编号 逗号分割", required = true)
    @NotNull(message = "所属角色不能为空")
    private String roleIds;

    @ApiModelProperty("数据权限类型")
    private Byte permissionType;

    @ApiModelProperty("数据权限部门id,逗号分割")
    private String deptIds;

    @ApiModelProperty("备注信息")
    private String remark;
}
