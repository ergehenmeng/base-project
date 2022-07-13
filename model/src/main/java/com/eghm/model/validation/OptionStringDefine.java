package com.eghm.model.validation;

import com.eghm.model.validation.annotation.OptionString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 二哥很猛
 * @date 2018/11/9 10:36
 */
public class OptionStringDefine implements ConstraintValidator<OptionString, String> {

    /**
     * 取值列表
     */
    private String[] values;

    /**
     * 是否必填
     */
    private boolean required;

    @Override
    public void initialize(OptionString constraintAnnotation) {
        this.values = constraintAnnotation.value();
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!required && value == null) {
            return true;
        }
        for (String v : values) {
            if (v.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
