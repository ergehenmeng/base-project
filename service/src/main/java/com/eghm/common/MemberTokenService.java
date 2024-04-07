package com.eghm.common;

import com.eghm.dto.ext.MemberToken;

/**
 * 会话令牌service
 *
 * @author 二哥很猛
 * @since 2018/8/14 17:35
 */
public interface MemberTokenService {

    /**
     * 创建登陆后的token,并将token放入缓存中
     * 1.创建基于
     *
     * @param memberId 用户信息
     * @param channel  访问渠道
     * @return token
     */
    MemberToken createToken(Long memberId, String channel);

    /**
     * 根据accessToken查找token
     *
     * @param accessToken token信息
     * @return token
     */
    MemberToken getByAccessToken(String accessToken);

    /**
     * 根据memberId查找token
     *
     * @param memberId memberId
     * @return token
     */
    MemberToken getByMemberId(Long memberId);

    /**
     * 清除用户token信息
     *
     * @param accessToken token
     */
    void cleanToken(String accessToken);

    /**
     * 清除用户信息
     *
     * @param memberId memberId
     */
    void cleanMemberId(Long memberId);

    /**
     * 清除refreshToken信息
     *
     * @param refreshToken 刷新token
     */
    void cleanRefreshToken(String refreshToken);

    /**
     * 刷新token
     *
     * @param refreshToken 刷新token
     * @return 新token
     */
    String refreshToken(String refreshToken);

    /**
     * 缓存登陆时所有需要保存的用户信息
     *
     * @param memberToken 用户登陆信息
     */
    void cacheToken(MemberToken memberToken);

}

