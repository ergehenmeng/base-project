package com.eghm.platform.iam.dto;

import com.eghm.foundation.core.convertor.RsaPasswordDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/9/17
 */
@Data
public class CheckPwdRequest {

    @Schema(description = "密码(rsa加密)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonDeserialize(using = RsaPasswordDeserializer.class)
    private String pwd;
}
