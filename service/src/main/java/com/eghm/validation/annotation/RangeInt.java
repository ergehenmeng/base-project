package com.eghm.validation.annotation;

import com.eghm.validation.RangeIntDefine;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * int范围判定
 * @author 二哥很猛
 * @date 2018/8/14 10:55
 */
@Documented
@Constraint(validatedBy = RangeIntDefine.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface RangeInt {

    /**
     * 错误信息 必须包含该属性
     * @return 错误信息
     */
    String message() default "非法参数";

    /**
     * 最小值
     * @return 默认0
     */
    int min() default 0;

    /**
     * 最大值
     * @return int最大值
     */
    int max() default Integer.MAX_VALUE;

    /**
     * 是否必须
     * @return 默认非必填
     */
    boolean required() default true;

    /**
     * 自定义校验必须包含该属性
     * @return 自定义校验必须包含该属性
     */
    Class<?>[] groups() default {};

    /**
     * 自定义校验必须包含该属性
     * @return 自定义校验必须包含该属性
     */
    Class<? extends Payload>[] payload() default {};
}
