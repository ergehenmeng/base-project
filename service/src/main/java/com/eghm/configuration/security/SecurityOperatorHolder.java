package com.eghm.configuration.security;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 获取用户登陆信息工具类
 * @author 殿小二
 * @date 2020/8/17
 */
public class SecurityOperatorHolder {

    private SecurityOperatorHolder() {
    }

    /**
     * 获取当前用户登陆的系统管理人员
     *
     * @return 系统登陆的用户
     */
    public static SecurityOperator getOperator() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object details = context.getAuthentication().getPrincipal();
        if (details != null) {
            return (SecurityOperator) details;
        }
        return null;
    }

    /**
     * 获取当前登陆的系统管理人员,找不到抛异常
     *
     * @return 系统用户
     */
    public static SecurityOperator getRequiredOperator() {
        SecurityOperator operator = getOperator();
        if (operator == null) {
            throw new BusinessException(ErrorCode.OPERATOR_TIMEOUT);
        }
        return operator;
    }

    /**
     * 登陆用户姓名
     */
    public static String getOperatorName() {
        return getRequiredOperator().getOperatorName();
    }

    /**
     * 登陆用户id
     */
    public static Long getOperatorId() {
        return getRequiredOperator().getId();
    }
}
