package com.eghm.dto.sys.user;

import com.eghm.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/11/30 16:37
 */
@Data
public class UserEditRequest {

    @ApiModelProperty(value = "id主键", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "用户名称", required = true)
    @NotBlank(message = "用户名称不能为空")
    private String nickName;

    @ApiModelProperty(value = "账户名", required = true)
    @NotBlank(message = "账户名不能为空")
    @Length(min = 6, max = 15, message = "账户名长度6-15位")
    private String userName;

    @ApiModelProperty(value = "手机号", required = true)
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "所属部门编号", required = true)
    private String deptCode;

    @ApiModelProperty(value = "角色编号", required = true)
    @NotEmpty(message = "请选择角色")
    private List<Long> roleIds;

    @ApiModelProperty("数据权限(1:本人 2:本部门 4:本部门及子部门 8:全部 16:自定义)")
    private Integer dataType;

    @ApiModelProperty("数据权限部门id,逗号分割")
    private String deptIds;

    @ApiModelProperty("备注信息")
    private String remark;
}
