package com.eghm.dto.sys.user;

import com.eghm.annotation.Assign;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 二哥很猛
 * @since 2018/11/26 10:05
 */
@Data
public class PasswordEditRequest {

    @Schema(description = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @Length(min = 32, max = 32, message = "旧密码格式错误")
    private String oldPwd;

    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @Length(min = 32, max = 32, message = "新密码格式错误")
    private String newPwd;

    @Assign
    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;
}
