package com.eghm.service.common;

import com.eghm.model.SysOperator;
import com.eghm.model.dto.ext.JwtToken;

import java.util.List;
import java.util.Optional;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
public interface JwtTokenService {

    /**
     * 根据用户信息创建jwt token
     * @param operator 用户信息
     * @return token
     */
    String createRefreshToken(SysOperator operator);

    /**
     * 根据用户信息创建 jwt refresh Token
     * @param operator 用户信息
     * @param authList 权限列表
     * @return token
     */
    String createToken(SysOperator operator, List<String> authList);

    /**
     * 解析token
     * @param token token
     * @return 解析出来的用户信息
     */
    Optional<JwtToken> parseToken(String token);
}
