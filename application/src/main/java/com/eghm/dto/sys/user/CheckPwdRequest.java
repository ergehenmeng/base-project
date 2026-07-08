package com.eghm.dto.sys.user;

import com.eghm.convertor.RsaPasswordDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.Expose;
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
    @Expose(serialize = false)
    @JsonDeserialize(using = RsaPasswordDeserializer.class)
    private String pwd;
}
