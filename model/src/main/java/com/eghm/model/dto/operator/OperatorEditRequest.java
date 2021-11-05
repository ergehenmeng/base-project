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

    /**
     * 主键
     */
    @ApiModelProperty("id主键")
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty("用户名称")
    @NotNull(message = "用户名称不能为空")
    private String operatorName;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @NotNull(message = "手机号不能为空")
    private String mobile;

    /**
     * 所属部门编号
     */
    @ApiModelProperty("所属部门编号")
    @NotNull(message = "所属部门不能为空")
    private String deptCode;

    /**
     * 角色列表
     */
    @ApiModelProperty("角色编号 逗号分割")
    @NotNull(message = "所属角色不能为空")
    private String roleIds;

    /**
     * 数据权限类型
     */
    @ApiModelProperty("数据权限类型")
    private Byte permissionType;

    /**
     * 自定义数据权限时的部门id
     */
    @ApiModelProperty("数据权限部门id,逗号分割")
    private String deptIds;

    /**
     * 备注信息
     */
    @ApiModelProperty("备注信息")
    private String remark;
}
