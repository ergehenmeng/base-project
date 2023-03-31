package com.eghm.service.common;

import com.eghm.dto.ext.RedisToken;

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
    RedisToken createToken(Long userId, String channel);

    /**
     * 根据accessToken查找token
     * @param accessToken token信息
     * @return token
     */
    RedisToken getByAccessToken(String accessToken);

    /**
     * 根据userId查找token
     * @param userId userId
     * @return token
     */
    RedisToken getByUserId(Long userId);

    /**
     * 获取token剩余过期时间
     * @param userId userId
     * @return 过期时间 小于0表示key没有过期时间或者已经清楚
     */
    long getTokenExpire(Long userId);

    /**
     * 根据刷新token信息获取用户登陆信息
     * @param refreshToken 刷新token
     * @return 用户登陆信息
     */
    RedisToken getByRefreshToken(String refreshToken);

    /**
     * 清除用户token信息
     * @param accessToken token
     */
    void cleanToken(String accessToken);

    /**
     * 清除用户信息
     * @param userId userId
     */
    void cleanUserId(Long userId);

    /**
     * 清除refreshToken信息
     * @param refreshToken 刷新token
     */
    void cleanRefreshToken(String refreshToken);

    /**
     * 缓存登陆时所有需要保存的用户信息
     *
     * @param redisToken 用户登陆信息
     */
    void cacheToken(RedisToken redisToken);

    /**
     * 缓存强制下线token信息
     * @param redisToken token
     * @param expire 过期时间
     */
    void cacheOfflineToken(RedisToken redisToken, long expire);

    /**
     * 获取用户强制被踢下线时保留的token信息
     * @param accessToken key
     * @return token信息
     */
    RedisToken getOfflineToken(String accessToken);

    /**
     * 清除被踢下线的token
     * @param offlineToken token
     */
    void cleanOfflineToken(String offlineToken);
}

