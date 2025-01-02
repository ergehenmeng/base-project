package com.eghm.dto.sys.register;

import com.eghm.validation.annotation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2019/9/3 14:26
 */
@Data
public class RegisterSmsDTO {

    @Mobile
    @Schema(description = "手机号码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mobile;

}
