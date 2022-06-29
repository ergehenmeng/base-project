package com.eghm.model.vo.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/1/28 18:23
 */
@Data
public class LoginResponse {

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("refreshToken")
    private String refreshToken;

    @ApiModelProperty("权限列表")
    private List<String> authorityList;
}
