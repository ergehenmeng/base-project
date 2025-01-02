package com.eghm.vo.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 二哥很猛
 * @since 2019/8/29 10:05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginTokenVO {

    @Schema(description = "登陆token")
    private String token;

    @Schema(description = "刷新token")
    private String refreshToken;
}
