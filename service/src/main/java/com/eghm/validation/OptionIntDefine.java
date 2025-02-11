package com.eghm.validation;

import com.eghm.validation.annotation.OptionInt;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author 二哥很猛
 * @since 2018/8/14 13:46
 */
public class OptionIntDefine implements ConstraintValidator<OptionInt, Integer> {

    /**
     * 取值列表
     */
    private int[] values;

    /**
     * 是否必填
     */
    private boolean required;

    @Override
    public void initialize(OptionInt constraintAnnotation) {
        this.values = constraintAnnotation.value();
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (!required && value == null) {
            return true;
        }
        if (value == null) {
            return false;
        }
        for (int v : values) {
            if (v == value) {
                return true;
            }
        }
        return false;
    }
}
