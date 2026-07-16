package com.eghm.foundation.core.validation.annotation;

import com.eghm.foundation.core.validation.AfterNowDefine;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 日期必须大于等于当前日期
 *
 * @author 二哥很猛
 * @since 2024/03/14
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@ReportAsSingleViolation
@Constraint(validatedBy = AfterNowDefine.class)
public @interface AfterNow {

    /**
     * 错误信息 必须包含该属性
     *
     * @return 错误信息
     */
    String message() default "请选择合法的日期";

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
