package com.eghm.dto.business.member;

import com.eghm.annotation.Assign;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 殿小二
 * @since 2020/8/29
 */
@Data
public class BindEmailDTO {

    @Schema(description = "绑定的邮箱号", requiredMode = Schema.RequiredMode.REQUIRED)
    @Email(message = "邮箱地址不能为空")
    private String email;

    @Schema(description = "邮箱验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空")
    @Length(min = 4, max = 6, message = "验证码格式不合法")
    private String authCode;

    @Assign
    @Schema(hidden = true)
    private Long memberId;
}
