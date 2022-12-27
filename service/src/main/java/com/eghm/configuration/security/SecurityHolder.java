package com.eghm.configuration.security;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.model.dto.ext.JwtManage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author 二哥很猛
 * @since 2022/11/4
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityHolder {

    private static final ThreadLocal<JwtManage> LOCAL = ThreadLocal.withInitial(JwtManage::new);

    public static void setToken(JwtManage token) {
        LOCAL.set(token);
    }

    /**
     * 获取当前登录用户的信息
     * @return 用户信息, 为空则抛登录过期异常
     */
    public static JwtManage getManageRequired() {
        JwtManage jwtManage = LOCAL.get();
        if (jwtManage == null) {
            throw new BusinessException(ErrorCode.LOGIN_EXPIRE);
        }
        return jwtManage;
    }

    /**
     * 获取当前登录用户的信息
     * @return 用户信息 可能为空
     */
    public static JwtManage getManage() {
        return LOCAL.get();
    }

    /**
     * 获取当前用户id
     * @return id
     */
    public static Long getManageId() {
        return getManageRequired().getId();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
