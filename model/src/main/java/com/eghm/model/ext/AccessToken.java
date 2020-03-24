package com.eghm.model.ext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * token令牌
 * @author 二哥很猛
 * @date 2018/8/14 17:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken implements Serializable {

    private static final long serialVersionUID = -539686478899884844L;

    /**
     * 用于签名
     */
    private String signKey;

    /**
     * token 保持登陆信息
     */
    private String accessToken;

    /**
     * 刷新token 不序列号保存到redis中
     */
    @JsonIgnore
    private String refreshToken;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 登陆来源 ANDROID IOS
     */
    private String channel;
}
