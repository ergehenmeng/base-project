package com.fanyin.validation;

import com.fanyin.validation.annotation.RangeDouble;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验规则定义
 * @author 二哥很猛
 * @date 2018/8/14 11:04
 */
public class RangeDoubleDefine implements ConstraintValidator<RangeDouble,Double> {

    /**
     * 最小值
     */
    private double min;

    /**
     * 最大值
     */
    private double max;

    /**
     * 是否必填
     */
    private boolean required;

    @Override
    public void initialize(RangeDouble constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return (!required && value == null ) || (value >= min && value <= max);
    }
}
