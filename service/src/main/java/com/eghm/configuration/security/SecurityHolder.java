package com.eghm.configuration.security;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.dto.ext.JwtUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author 二哥很猛
 * @since 2022/11/4
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityHolder {

    /**
     * 异步也能获取到用户信息
     */
    private static final TransmittableThreadLocal<JwtUser> LOCAL = TransmittableThreadLocal.withInitial(JwtUser::new);

    public static void setToken(JwtUser token) {
        LOCAL.set(token);
    }

    /**
     * 获取当前登录用户的信息
     * @return 用户信息, 为空则抛登录过期异常
     */
    public static JwtUser getUserRequired() {
        JwtUser jwtUser = LOCAL.get();
        if (jwtUser == null) {
            throw new BusinessException(ErrorCode.LOGIN_EXPIRE);
        }
        return jwtUser;
    }

    /**
     * 获取当前登录用户的信息
     * @return 用户信息 可能为空
     */
    public static JwtUser getUser() {
        return LOCAL.get();
    }

    /**
     * 获取当前用户id
     * @return id
     */
    public static Long getUserId() {
        return getUserRequired().getId();
    }

    /**
     * 获取当前用户对应的商户id
     * @return 商户id, 注意: 如果用户没关联商户,则为空
     */
    public static Long getMerchantId() {
        return getUserRequired().getMerchantId();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
