package com.eghm.common;


import com.eghm.dto.ext.UserToken;
import com.eghm.model.SysUser;

import java.util.List;
import java.util.Optional;

/**
 * @author 殿小二
 * @since 2020/8/28
 */
public interface UserTokenService {

    /**
     * 根据用户信息创建 jwt refresh Token
     *
     * @param user       用户信息
     * @param authList   菜单权限列表
     * @param dataList   自定义数据权限
     * @return token
     */
    String createToken(SysUser user, List<String> authList, List<String> dataList);

    /**
     * 解析token
     *
     * @param token token
     * @return 解析出来的用户信息
     */
    Optional<UserToken> parseToken(String token);

    /**
     * 退出登录
     *
     * @param token token
     */
    void logout(String token);
}