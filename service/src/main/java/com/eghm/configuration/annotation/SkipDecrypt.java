package com.eghm.configuration.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 已经登陆的用户,强制会对请求进行解密操作,如果不需要解密则添加该注解
 * @author 二哥很猛
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface SkipDecrypt {
}
