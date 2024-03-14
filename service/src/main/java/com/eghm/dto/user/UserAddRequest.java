package com.eghm.dto.user;

import com.eghm.enums.DataType;
import com.eghm.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 管理人员 添加
 *
 * @author 二哥很猛
 * @since 2018/11/30 15:43
 */
@Data
public class UserAddRequest {

    @ApiModelProperty(value = "用户名称", required = true)
    @NotBlank(message = "用户名称不能为空")
    private String userName;

    @ApiModelProperty(value = "手机号", required = true)
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "所属部门编号", required = true)
    @NotBlank(message = "所属部门不能为空")
    private String deptCode;

    @ApiModelProperty(value = "角色编号", required = true)
    @NotEmpty(message = "请选择角色")
    private List<Long> roleIds;

    @ApiModelProperty("数据权限")
    private DataType dataType;

    @ApiModelProperty("数据权限部门id,逗号分割")
    private String deptIds;

    @ApiModelProperty("备注信息")
    private String remark;
}
