package com.eghm.app.webapp.annotation;

import com.eghm.app.webapp.configuration.interceptor.ApiSignInterceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 针对提供给第三方使用的接口,签名校验使用 <br/>
 * 注意: 被注解的接口, 不能包含@SkipLogger注解, 否则会导致请求参数被过滤, 导致签名校验失败
 * @author 二哥很猛
 * @see ApiSignInterceptor 登陆校验规则
 * @since 2023/10/20 19:04
 */
@Documented
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface ApiSign {

}
