package com.eghm.dto.business.member;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 殿小二
 * @since 2020/9/3
 */
@Data
public class ChangeEmailDTO {

    @ApiModelProperty(value = "手机号或邮箱", required = true)
    @Email(message = "邮箱格式错误")
    private String email;

    @ApiModelProperty(value = "邮箱验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    @Length(min = 4, max = 6, message = "验证码格式不合法")
    private String authCode;

    @Assign
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long memberId;

}
