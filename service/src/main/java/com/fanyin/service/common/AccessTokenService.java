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
     * @return 缓存的对象
     */
    AccessToken saveByAccessKey(AccessToken token);

    /**
     * 保存token
     * @param token token对象
     * @return token信息
     */
    AccessToken saveByUserId(AccessToken token);

}

