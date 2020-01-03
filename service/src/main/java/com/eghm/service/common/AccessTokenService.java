package com.eghm.service.common;

import com.eghm.model.ext.AccessToken;

/**
 * 会话令牌service
 * @author 二哥很猛
 * @date 2018/8/14 17:35
 */
public interface AccessTokenService {

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
    void saveByAccessToken(AccessToken token);

    /**
     * 保存 userId:token关系
     * @param userId   用户id
     * @param accessToken token
     */
    void saveByUserId(int userId,String accessToken);

}

