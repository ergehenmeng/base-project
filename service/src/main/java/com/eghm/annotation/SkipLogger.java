package com.eghm.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>管理后台项目默认记录POST请求日志</p>
 * <p>移动端默认记录全部日志</p>
 * 如需不添加日志记录,则添加该注解(移动端和管理后台均有效)
 *
 * @author 二哥很猛
 */
@Documented
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface SkipLogger {
}
