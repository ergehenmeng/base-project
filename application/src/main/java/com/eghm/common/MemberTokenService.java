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
     * 删除指定渠道用户的token,包含刷新token以及映射token等
     * 注意: 如果channel为空,则删除所有渠道的token
     *
     * @param memberId 会员id
     * @param channel  对应渠道
     */
    void cleanToken(Long memberId, String channel);

    /**
     * 刷新token
     *
     * @param refreshToken 刷新token
     * @return 新token
     */
    String refreshToken(String refreshToken);

}

