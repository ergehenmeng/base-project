package com.eghm.dto.sys.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 二哥很猛
 * @since 2022/1/28 17:26
 */
@Data
public class TotpBindRequest {

    @Schema(description = "序列号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "序列号不能为空")
    private String uuid;

    @Schema(description = "核对码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "核对码不能为空")
    @Length(min = 32, max = 32, message = "核对码格式错误")
    private String secretKey;
}
