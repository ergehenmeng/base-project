package com.eghm.configuration.security;

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

    private static final ThreadLocal<JwtOperator> LOCAL = ThreadLocal.withInitial(JwtOperator::new);

    public static void setToken(JwtOperator token) {
        LOCAL.set(token);
    }

    public static JwtOperator getOperatorRequired() {
        JwtOperator jwtOperator = LOCAL.get();
        if (jwtOperator == null) {
            throw new BusinessException(ErrorCode.LOGIN_EXPIRE);
        }
        return jwtOperator;
    }

    public static Long getOperatorId() {
        return getOperatorRequired().getId();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
