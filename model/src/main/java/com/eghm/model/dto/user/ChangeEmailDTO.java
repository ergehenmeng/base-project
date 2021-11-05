package com.eghm.model.dto.user;

import com.eghm.model.annotation.Sign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @author 殿小二
 * @date 2020/9/3
 */
@Data
public class ChangeEmailDTO {

    /**
     * 邮箱账号
     */
    @ApiModelProperty(value = "手机号或邮箱",required = true)
    @Email(message = "邮箱格式错误")
    private String email;

    /**
     * 邮箱验证码
     */
    @ApiModelProperty(value = "邮箱验证码",required = true)
    @Email(message = "邮箱验证码不能为空")
    private String authCode;

    /**
     * 用户id
     */
    @Sign
    @ApiModelProperty(hidden = true)
    private Long userId;

}
