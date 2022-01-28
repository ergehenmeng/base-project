package com.eghm.service.common;

import com.eghm.model.dto.ext.JwtToken;

import java.util.List;
import java.util.Optional;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
public interface JwtTokenService {

    /**
     * 创建token
     * @param userId 用户id
     * @param channel 登陆渠道
     * @return token
     */
    String createRefreshToken(Long userId, String channel);

    /**
     * 创建token 包含权限
     * @param userId 用户id
     * @param channel 登陆渠道
     * @param authList 权限列表
     * @return token
     */
    String createToken(Long userId, String channel, List<String> authList);

    /**
     * 解析token
     * @param token token
     * @return 解析出来的用户信息
     */
    Optional<JwtToken> parseToken(String token);
}
