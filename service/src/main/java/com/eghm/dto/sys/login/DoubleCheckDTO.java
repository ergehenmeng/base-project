package com.eghm.dto.sys.login;

import com.eghm.annotation.Assign;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2019/8/19 16:55
 */
@Data
public class DoubleCheckDTO {

    @NotEmpty(message = "请求id不能为空")
    @Schema(description = "请求id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uuid;

    @Schema(description = "短信验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空")
    private String smsCode;

    @Schema(description = "ip", hidden = true)
    @Assign
    private String ip;
}
