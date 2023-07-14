package com.eghm.service.common;

import com.eghm.model.SysUser;
import com.eghm.dto.ext.UserToken;

import java.util.List;
import java.util.Optional;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
public interface AccessTokenService {

    /**
     * 根据用户信息创建 jwt refresh Token
     * @param user 用户信息
     * @param merchantId 商户id
     * @param authList 权限列表
     * @return token
     */
    String createToken(SysUser user, Long merchantId, List<String> authList);

    /**
     * 解析token
     * @param token token
     * @return 解析出来的用户信息
     */
    Optional<UserToken> parseToken(String token);
}
