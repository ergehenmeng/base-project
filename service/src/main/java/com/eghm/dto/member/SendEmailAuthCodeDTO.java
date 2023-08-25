package com.eghm.dto.member;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Data
public class SendEmailAuthCodeDTO {

    @ApiModelProperty(value = "手机号或邮箱",required = true)
    @Email(message = "邮箱格式错误")
    private String email;

    @ApiModelProperty(value = "短信验证码")
    private String smsCode;

    @Assign
    @ApiModelProperty(hidden = true)
    private Long memberId;

}
