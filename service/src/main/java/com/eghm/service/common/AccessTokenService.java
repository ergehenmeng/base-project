package com.eghm.service.common;

import com.eghm.dao.model.user.User;
import com.eghm.model.ext.AccessToken;

/**
 * 会话令牌service
 * @author 二哥很猛
 * @date 2018/8/14 17:35
 */
public interface AccessTokenService {

    /**
     * 创建登陆后的token,并将token放入缓存中
     *
     * @param user    用户信息
     * @param channel 访问渠道
     * @return token
     */
    AccessToken createAccessToken(User user, String channel);

    /**
     * 根据accessToken查找token
     * @param accessToken token信息
     * @return token
     */
    AccessToken getByAccessToken(String accessToken);

    /**
     * 根据userId查找token
     * @param userId userId
     * @return token
     */
    String getByUserId(int userId);

    /**
     * 保存token
     * @param token token对象
     */
    void cacheByAccessToken(AccessToken token);

    /**
     * 保存 userId:token关系
     * @param userId   用户id
     * @param accessToken token
     */
    void cacheByUserId(int userId,String accessToken);

    /**
     * 清除用户token信息
     * @param accessToken token
     */
    void cleanAccessToken(String accessToken);

    /**
     * 清除用户信息
     * @param userId userId
     */
    void cleanUserId(Integer userId);
}

