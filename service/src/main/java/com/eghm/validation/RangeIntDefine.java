package com.eghm.validation;

import com.eghm.validation.annotation.RangeInt;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验规则定义
 *
 * @author 二哥很猛
 * @since 2018/8/14 11:04
 */
public class RangeIntDefine implements ConstraintValidator<RangeInt, Integer> {

    /**
     * 最小值
     */
    private int min;

    /**
     * 最大值
     */
    private int max;

    /**
     * 是否必填
     */
    private boolean required;

    @Override
    public void initialize(RangeInt constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return (!required && value == null) || (value != null && value >= min && value <= max);
    }
}
