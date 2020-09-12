package com.eghm.web.annotation;

import com.eghm.web.configuration.resolver.JsonExtractHandlerArgumentResolver;
import com.eghm.web.configuration.interceptor.TokenInterceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 跳过登陆认证 注意跳过登陆后,如果用户真实登陆,依旧可以获取到userId
 *
 * @see TokenInterceptor 登陆校验规则
 * @see JsonExtractHandlerArgumentResolver 请求参数解密
 * @author 二哥很猛
 * @date 2019/11/20 16:04
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface SkipAccess {
}
