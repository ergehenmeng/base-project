package com.eghm.vo.login;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author 二哥很猛
 * @since 2019/8/29 10:05
 */
public record LoginTokenVO(@Schema(description = "登陆token") String token,
                           @Schema(description = "刷新token") String refreshToken) {
}
