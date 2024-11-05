package com.eghm.dto.business.member;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author 殿小二
 * @since 2020/8/29
 */
@Data
public class BindEmailDTO {

    @ApiModelProperty(value = "绑定的邮箱号", required = true)
    @Email(message = "邮箱地址不能为空")
    private String email;

    @ApiModelProperty(value = "邮箱验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    @Length(min = 4, max = 6, message = "验证码格式不合法")
    private String authCode;

    @Assign
    @ApiModelProperty(hidden = true)
    private Long memberId;
}
