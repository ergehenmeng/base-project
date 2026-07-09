package com.eghm.application.shared.dto.sys.user;

import com.eghm.application.shared.annotation.Assign;
import com.eghm.application.shared.validation.annotation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2018/11/26 10:05
 */
@Data
public class UserProfileRequest {

    @Schema(description = "昵称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "昵称不能为空")
    @Mobile
    private String mobile;

    @Assign
    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;
}
