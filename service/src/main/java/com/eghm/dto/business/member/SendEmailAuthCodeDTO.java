package com.eghm.dto.business.member;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 殿小二
 * @since 2020/8/28
 */
@Data
public class SendEmailAuthCodeDTO {

    @ApiModelProperty(value = "手机号或邮箱", required = true)
    @Email(message = "邮箱格式错误")
    private String email;

    @ApiModelProperty(value = "短信验证码", required = true)
    @NotBlank(message = "短信验证码不能为空")
    private String smsCode;

    @Assign
    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long memberId;

}
