package com.eghm.dto.user;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.Password;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2018/11/26 10:05
 */
@Data
public class PasswordEditRequest {

    @ApiModelProperty(value = "旧密码", required = true)
    @NotBlank(message = "原密码不能为空")
    private String oldPwd;

    @ApiModelProperty(value = "新密码", required = true)
    @Password(message = "新密码必须包含英文字符、数字、@#&_")
    @Size(min = 6, max = 20, message = "新密码长度应在6~20位之间")
    private String newPwd;

    @Assign
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;
}
