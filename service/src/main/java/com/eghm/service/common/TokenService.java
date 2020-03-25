package com.eghm.service.common;

import com.eghm.model.ext.Token;

/**
 * 会话令牌service
 * @author 二哥很猛
 * @date 2018/8/14 17:35
 */
public interface TokenService {

    /**
     * 创建登陆后的token,并将token放入缓存中
     * 1.创建基于
     * @param userId    用户信息
     * @param channel 访问渠道
     * @return token
     */
    Token createToken(int userId, String channel);

    /**
     * 根据accessToken查找token
     * @param accessToken token信息
     * @return token
     */
    Token getByAccessToken(String accessToken);

    /**
     * 根据userId查找token
     * @param userId userId
     * @return token
     */
    Token getByUserId(int userId);

    /**
     * 根据刷新token信息获取用户登陆信息
     * @param refreshToken 刷新token
     * @return 用户登陆信息
     */
    Token getByRefreshToken(String refreshToken);

    /**
     * 清除用户token信息
     * @param accessToken token
     */
    void cleanAccessToken(String accessToken);

    /**
     * 清除用户信息
     * @param userId userId
     */
    void cleanUserId(int userId);

    /**
     * 清除refreshToken信息
     * @param refreshToken 刷新token
     */
    void cleanRefreshToken(String refreshToken);

    /**
     * 缓存登陆时所有需要保存的用户信息
     *
     * @param token 用户登陆信息
     */
    void cacheToken(Token token);
}

