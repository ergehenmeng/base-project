package com.eghm.model.dto.user;

import com.eghm.common.annotation.Sign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @author 殿小二
 * @date 2020/8/29
 */
@Data
public class BindEmailDTO {

    @ApiModelProperty(value = "绑定的邮箱号",required = true)
    @Email(message = "邮箱地址不能为空")
    private String email;

    @ApiModelProperty(value = "邮箱验证码",required = true)
    @Email(message = "邮箱验证码不能为空")
    private String authCode;

    @Sign
    @ApiModelProperty(hidden = true)
    private Long userId;
}
