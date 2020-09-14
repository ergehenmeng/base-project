package com.eghm.service.common;

import com.eghm.model.dto.ext.JwtToken;

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
    String createToken(int userId, String channel);

    /**
     * 验证token是否合法
     * @param token token
     * @return 解析出来的用户信息
     */
    JwtToken verifyToken(String token);
}
