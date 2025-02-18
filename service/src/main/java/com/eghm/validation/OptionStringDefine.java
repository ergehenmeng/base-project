package com.eghm.validation;

import com.eghm.validation.annotation.OptionString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.eghm.utils.StringUtil.isBlank;

/**
 * @author 二哥很猛
 * @since 2018/11/9 10:36
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
        if (!required && isBlank(value)) {
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
