package com.eghm.dto.sys.login;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

/**
 * @author 二哥很猛
 * @since 2019/8/19 16:55
 */
@Data
public class AccountLoginDTO {

    @NotEmpty(message = "登陆账号不能为空")
    @Schema(description = "手机号或邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
    private String account;

    @Password(message = "密码格式错误")
    @Schema(description = "密码,MD5小写加密过", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    private String pwd;

    @Schema(description = "ip", hidden = true)
    @Assign
    private String ip;

    @Schema(description = "设备唯一编号", hidden = true)
    @Assign
    private String serialNumber;
}
