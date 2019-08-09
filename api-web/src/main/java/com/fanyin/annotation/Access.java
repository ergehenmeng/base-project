package com.fanyin.annotation;


import com.fanyin.common.enums.Channel;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 访问权限校验 主要针对前后端分离接口
 * @author 二哥很猛
 * @date 2018/8/14 16:19
 */
@Documented
@Target({METHOD,TYPE})
@Retention(RUNTIME)
public @interface Access {

    /**
     * 是否需要登陆(即access校验)
     * @return 默认是
     */
    boolean access() default true;

    /**
     * 客户端类型
     * @return 默认后台请求
     */
    Channel[] value() default Channel.PC;

}
