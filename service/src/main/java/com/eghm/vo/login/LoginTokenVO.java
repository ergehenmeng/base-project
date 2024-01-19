package com.eghm.vo.login;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("登陆token")
    private String token;

    @ApiModelProperty("刷新token")
    private String refreshToken;
}
