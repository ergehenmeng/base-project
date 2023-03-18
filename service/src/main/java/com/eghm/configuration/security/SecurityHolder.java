package com.eghm.configuration.security;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.model.dto.ext.JwtOperator;
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
    private static final TransmittableThreadLocal<JwtOperator> LOCAL = TransmittableThreadLocal.withInitial(JwtOperator::new);

    public static void setToken(JwtOperator token) {
        LOCAL.set(token);
    }

    /**
     * 获取当前登录用户的信息
     * @return 用户信息, 为空则抛登录过期异常
     */
    public static JwtOperator getOperatorRequired() {
        JwtOperator jwtOperator = LOCAL.get();
        if (jwtOperator == null) {
            throw new BusinessException(ErrorCode.LOGIN_EXPIRE);
        }
        return jwtOperator;
    }

    /**
     * 获取当前登录用户的信息
     * @return 用户信息 可能为空
     */
    public static JwtOperator getOperator() {
        return LOCAL.get();
    }

    /**
     * 获取当前用户id
     * @return id
     */
    public static Long getOperatorId() {
        return getOperatorRequired().getId();
    }

    /**
     * 获取当前用户对应的商户id
     * @return 商户id, 注意: 如果用户没关联商户,则为空
     */
    public static Long getMerchantId() {
        return getOperatorRequired().getMerchantId();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
