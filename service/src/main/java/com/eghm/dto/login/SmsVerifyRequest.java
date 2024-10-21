package com.eghm.dto.login;

import com.eghm.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2019/8/20 10:50
 */
@Data
public class SmsVerifyRequest {

    @Mobile
    @ApiModelProperty(value = "手机号码", required = true)
    private String mobile;

    @ApiModelProperty(value = "图形验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;
}
