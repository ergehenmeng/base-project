package com.eghm.dto.member;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @author 殿小二
 * @date 2020/9/3
 */
@Data
public class ChangeEmailDTO {

    @ApiModelProperty(value = "手机号或邮箱",required = true)
    @Email(message = "邮箱格式错误")
    private String email;

    @ApiModelProperty(value = "邮箱验证码",required = true)
    @Email(message = "邮箱验证码不能为空")
    private String authCode;

    @Assign
    @ApiModelProperty(hidden = true)
    private Long memberId;

}
