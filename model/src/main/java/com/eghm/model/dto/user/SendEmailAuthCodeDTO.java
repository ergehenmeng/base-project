package com.eghm.model.dto.user;

import com.eghm.model.annotation.Sign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Data
public class SendEmailAuthCodeDTO {

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "手机号或邮箱",required = true)
    @Email(message = "邮箱格式错误")
    private String email;

    /**
     * 短信验证码
     */
    @ApiModelProperty(value = "短信验证码")
    private String smsCode;

    /**
     * 用户id
     */
    @Sign
    @ApiModelProperty(hidden = true)
    private Long userId;

}
