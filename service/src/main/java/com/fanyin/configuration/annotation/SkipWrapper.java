package com.fanyin.configuration.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 对@ResponseBody返回不进行RespBody对象的包装,可添加该注解
 * @author 二哥很猛
 * @date 2019/9/6 17:59
 */
@Documented
@Target({METHOD,TYPE})
@Retention(RUNTIME)
public @interface SkipWrapper {
}
