package com.fanyin.service.common;

import com.fanyin.model.ext.AccessToken;

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
    AccessToken getByAccessKey(String accessKey);

    /**
     * 根据userId查找token
     * @param userId userId
     * @return token
     */
    AccessToken getByUserId(int userId);

    /**
     * 保存token
     * @param token token对象
     */
    void saveByAccessKey(AccessToken token);

    /**
     * 保存token
     * @param token token对象
     */
    void saveByUserId(AccessToken token);

    /**
     * 创建登陆后的token,并将token放入缓存中
     * @param userId 用户id
     * @param requestType 登陆来源 ANDROID,IOS
     * @return token
     */
    AccessToken createAccessToken(int userId, String requestType);
}

