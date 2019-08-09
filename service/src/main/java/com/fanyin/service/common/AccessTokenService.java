package com.fanyin.service.common;

import com.fanyin.ext.AccessToken;

/**
 * 会话令牌service
 * @author 二哥很猛
 * @date 2018/8/14 17:35
 */
public interface AccessTokenService {

    /**
     * 根据accessKey查找token
     * @param accessKey accessKey
     * @return token
     */
    AccessToken getAccessToken(String accessKey);

    /**
     * 保存token 30分钟超时时间
     * @param token token对象
     */
    void saveAccessToken(AccessToken token);

    /**
     * 创建登陆后的token,并将token放入缓存中
     * @param userId 用户id
     * @param source 登陆来源 ANDROID,IOS
     * @return token
     */
    AccessToken createAccessToken(int userId, String source);
}

