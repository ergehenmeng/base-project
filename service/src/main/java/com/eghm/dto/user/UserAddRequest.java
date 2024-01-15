package com.eghm.dto.user;

import com.eghm.enums.DataType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 管理人员 添加
 * @author 二哥很猛
 * @date 2018/11/30 15:43
 */
@Data
public class UserAddRequest {

    @ApiModelProperty(value = "用户名称", required = true)
    @NotNull(message = "用户名称不能为空")
    private String userName;

    @ApiModelProperty(value = "手机号", required = true)
    @NotNull(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "所属部门编号", required = true)
    @NotNull(message = "所属部门不能为空")
    private String deptCode;

    @ApiModelProperty(value = "角色编号 逗号分割", required = true)
    @Size(message = "所属角色不能为空")
    private List<Long> roleIds;

    @ApiModelProperty("数据权限")
    private DataType dataType;

    @ApiModelProperty("数据权限部门id,逗号分割")
    private String deptIds;

    @ApiModelProperty("备注信息")
    private String remark;
}
