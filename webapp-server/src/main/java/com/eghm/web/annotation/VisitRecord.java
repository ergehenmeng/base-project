package com.eghm.web.annotation;

import com.eghm.enums.VisitType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 会员浏览记录
 *
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface VisitRecord {

    /**
     * 页面类型
     * @return 类型
     */
    VisitType value();
}
