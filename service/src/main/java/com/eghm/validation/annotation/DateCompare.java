package com.eghm.validation.annotation;

import com.eghm.validation.DateCompareDefine;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 比较两个日期的大小
 *
 * @author 二哥很猛
 * @since 2023/10/14 16:21
 */
@Documented
@Constraint(validatedBy = DateCompareDefine.class)
@Target(METHOD)
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface DateCompare {

    /**
     * 错误信息 必须包含该属性
     *
     * @return 错误信息
     */
    String message() default "截止日期不能小于开始日期";

    /**
     * 自定义校验必须包含该属性
     *
     * @return 自定义校验必须包含该属性
     */
    Class<?>[] groups() default {};

    /**
     * 自定义校验必须包含该属性
     *
     * @return 自定义校验必须包含该属性
     */
    Class<? extends Payload>[] payload() default {};
}
