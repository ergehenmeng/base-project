package com.eghm.platform.iam.dto;

import com.eghm.foundation.core.annotation.Assign;
import com.eghm.foundation.core.convertor.RsaPasswordDeserializer;
import com.eghm.foundation.core.validation.annotation.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2018/11/26 10:05
 */
@Data
public class PasswordEditRequest {

    @Schema(description = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "旧密码不能为空")
    @JsonDeserialize(using = RsaPasswordDeserializer.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String oldPwd;

    @Schema(description = "新密码-英文字符、数字、@#&_(rsa加密)", requiredMode = Schema.RequiredMode.REQUIRED)
    @Password(message = "新密码格式错误")
    @JsonDeserialize(using = RsaPasswordDeserializer.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newPwd;

    @Assign
    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;
}
