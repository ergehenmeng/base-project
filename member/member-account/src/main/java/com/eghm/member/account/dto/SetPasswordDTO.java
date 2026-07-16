package com.eghm.member.account.dto;

import com.eghm.foundation.core.convertor.RsaPasswordDeserializer;
import com.eghm.foundation.core.validation.annotation.Password;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2021/12/26 19:22
 */
@Data
public class SetPasswordDTO {

    @Schema(description = "请求唯一ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请求ID不能为空")
    private String requestId;

    @Schema(description = "密码(rsa加密)", requiredMode = Schema.RequiredMode.REQUIRED)
    @Password
    @JsonDeserialize(using = RsaPasswordDeserializer.class)
    @Expose(serialize = false)
    private String password;
}
