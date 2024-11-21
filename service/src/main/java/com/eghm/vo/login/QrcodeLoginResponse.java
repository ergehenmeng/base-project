package com.eghm.vo.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/11/21
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QrcodeLoginResponse {

    @ApiModelProperty("登录成功后的信息")
    private LoginResponse data;

    @ApiModelProperty("状态码 0:未绑定(跳转到登录页面) 1:已绑定(绑定后登录信息不为空)")
    private Integer state;
}
