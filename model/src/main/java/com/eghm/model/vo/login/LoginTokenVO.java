package com.eghm.model.vo.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/8/29 10:05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class LoginTokenVO implements Serializable {

    private static final long serialVersionUID = 5945495590088977331L;

    /**
     * secret
     */
    @ApiModelProperty("签名secret")
    private String secret;

    /**
     * token
     */
    @ApiModelProperty("登陆accessToken")
    private String accessToken;

    /**
     * refreshToken
     */
    @ApiModelProperty("刷新token")
    private String refreshToken;
}
