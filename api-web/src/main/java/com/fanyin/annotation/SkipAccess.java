package com.fanyin.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 跳过登陆认证
 * @author 二哥很猛
 * @date 2019/11/20 16:04
 */
@Documented
@Target({METHOD,TYPE})
@Retention(RUNTIME)
public @interface SkipAccess {
}
