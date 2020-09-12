package com.eghm.web.annotation;

import com.eghm.web.configuration.handler.RequestResponseLogAspect;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * api项目默认记录所有请求日志,如需不添加日志记录,则添加该注解
 *
 * @see RequestResponseLogAspect aop请求日志记录
 * @author 二哥很猛
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface SkipLogger {
}
