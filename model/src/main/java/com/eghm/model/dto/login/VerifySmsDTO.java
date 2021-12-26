package com.eghm.model.dto.login;

import com.eghm.model.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wyb
 * @date 2021/12/26 19:40
 */
@Data
public class VerifySmsDTO {

    @ApiModelProperty(value = "手机号", required = true)
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "验证码", required = true)
    @NotNull(message = "验证码不能为空")
    private String smsCode;
}
