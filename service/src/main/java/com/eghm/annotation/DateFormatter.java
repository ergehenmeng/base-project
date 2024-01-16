package com.eghm.annotation;

import java.lang.annotation.*;
import java.time.temporal.ChronoUnit;

/**
 * @author 二哥很猛
 * @since 2023/11/21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface DateFormatter {

    /**
     * 日期格式化类型 yyyy-MM-dd HH:mm:ss或 yyyy-MM-dd
     *
     * @return 格式类型
     */
    String pattern() default "";

    /**
     * 偏移量
     *
     * @return 0
     */
    long offset() default 0;

    /**
     * 偏移单位
     *
     * @return 单位
     */
    ChronoUnit unit() default ChronoUnit.DAYS;
}
