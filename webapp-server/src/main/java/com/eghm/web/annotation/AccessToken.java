package com.eghm.web.annotation;

import com.eghm.dto.ext.ApiHolder;
import com.eghm.web.configuration.interceptor.TokenInterceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 登陆认证, 在方法上添加该注解,即可通过 {@link ApiHolder} 获取到用户信息 <br/>
 * 注意: 如果方法上未添加该注解, 但是用户确实是已登陆状态, 依旧可以获取用户信息 <br/>
 * 如果无法确认用是否登陆,请在获取用户信息时给予必要的非空判断
 *
 * @author 二哥很猛
 * @see TokenInterceptor 登陆校验规则
 * @see ApiHolder 用户信息
 * @since 2019/11/20 16:04
 */
@Documented
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface AccessToken {
}
