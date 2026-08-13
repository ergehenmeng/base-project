package com.eghm.member.account.dto;

import com.eghm.foundation.core.annotation.Assign;
import com.eghm.foundation.core.convertor.RsaPasswordDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2019/8/19 16:55
 */
@Data
public class AccountLoginDTO {

    @NotEmpty(message = "登陆账号不能为空")
    @Schema(description = "手机号或邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
    private String account;

    @Schema(description = "密码(rsa加密)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonDeserialize(using = RsaPasswordDeserializer.class)
    private String pwd;

    @Schema(description = "ip", hidden = true)
    @Assign
    private String ip;

    @Schema(description = "设备唯一编号", hidden = true)
    @Assign
    private String serialNumber;
}
