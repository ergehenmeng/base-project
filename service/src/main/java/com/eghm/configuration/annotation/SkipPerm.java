package com.eghm.configuration.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 管理后台跳过菜单及按钮权限校验标识符
 *
 * @author 二哥很猛
 * @since 2022/11/4
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface SkipPerm {
}
