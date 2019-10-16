package com.fanyin.model.vo.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @date 2019/10/15 9:49
 */
@Data
public class LoginTokenPo {

    /**
     * accessKey
     */
    @ApiModelProperty("登陆accessKey")
    private String accessKey;

    /**
     * token
     */
    @ApiModelProperty("登陆accessToken")
    private String accessToken;
}
