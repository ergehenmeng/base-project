package com.eghm.controller;


import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.security.SecurityOperator;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSession;

/**
 * @author 二哥很猛
 */
public class AbstractController {

    /**
     * 存放session
     *
     * @param session session对象对象
     * @param key     sessionKey
     * @param value   session值
     */
    protected void putSession(HttpSession session, String key, Object value) {
        session.setAttribute(key, value);
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
    public static Integer getOperatorId() {
        return getRequiredOperator().getId();
    }

}
