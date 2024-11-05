package com.eghm.web.annotation;

import com.eghm.enums.SignType;
import com.eghm.web.configuration.interceptor.SignCheckInterceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 针对提供给第三方使用的接口,签名校验使用 <br/>
 * 只支持post接口签名
 *
 * @author 二哥很猛
 * @since 2023/10/20 19:04
 * @see SignCheckInterceptor 登陆校验规则
 */
@Documented
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface SignCheck {

    /**
     * 签名方式
     *
     * @return 签名方式
     */
    SignType value() default SignType.MD5;
}
