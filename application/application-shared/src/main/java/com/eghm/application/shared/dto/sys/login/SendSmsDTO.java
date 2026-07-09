package com.eghm.application.shared.dto.sys.login;

import com.eghm.application.shared.validation.annotation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2019/8/20 10:50
 */
@Data
public class SendSmsDTO {

    @Mobile
    @Schema(description = "手机号码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mobile;

}
