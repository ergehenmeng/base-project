package com.fanyin.validation.annotation;

import com.fanyin.validation.RangeLengthDefine;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 字符串长度校验 只适用string
 * @author 二哥很猛
 * @date 2018/8/14 11:24
 */
@Documented
@Constraint(validatedBy = RangeLengthDefine.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface RangeLength {

    /**
     * 错误信息 必须包含该属性
     * @return 错误信息
     */
    String message() default "非法参数";

    /**
     * 最小长度
     * @return 长度
     */
    long min() default 0;

    /**
     * 最大长度
     * @return int最大值
     */
    long max() default Long.MAX_VALUE;

    /**
     * 是否必填
     * @return 默认非必填
     */
    boolean required() default false;

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

