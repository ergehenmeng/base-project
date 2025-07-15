package com.eghm.dto.sys.user;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.Password;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2018/11/26 10:05
 */
@Data
public class PasswordEditRequest {

    @ApiModelProperty(value = "旧密码", required = true)
    @NotBlank(message = "旧密码不能为空")
    private String oldPwd;

    @ApiModelProperty(value = "新密码", required = true)
    @Password(message = "新密码格式错误")
    private String newPwd;

    @Assign
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;
}
