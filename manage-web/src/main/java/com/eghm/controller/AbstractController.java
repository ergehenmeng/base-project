package com.eghm.controller;


import com.eghm.configuration.security.SecurityOperator;
import com.eghm.configuration.security.SecurityOperatorHolder;

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
        return SecurityOperatorHolder.getOperator();
    }

    /**
     * 获取当前登陆的系统管理人员,找不到抛异常
     *
     * @return 系统用户
     */
    public static SecurityOperator getRequiredOperator() {
        return SecurityOperatorHolder.getRequiredOperator();
    }

    /**
     * 登陆用户姓名
     */
    public static String getOperatorName() {
        return SecurityOperatorHolder.getOperatorName();
    }

    /**
     * 登陆用户id
     */
    public static Integer getOperatorId() {
        return SecurityOperatorHolder.getOperatorId();
    }

}
