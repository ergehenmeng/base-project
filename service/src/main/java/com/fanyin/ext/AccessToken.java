package com.fanyin.ext;

import lombok.Data;

import java.io.Serializable;

/**
 * token令牌
 * @author 二哥很猛
 * @date 2018/8/14 17:37
 */
@Data
public class AccessToken implements Serializable {

    private static final long serialVersionUID = -539686478899884844L;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * token
     */
    private String accessToken;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 登陆来源 ANDROID IOS
     */
    private String requestType;
}
