package com.fanyin.validation;

import com.fanyin.common.utils.RegExpUtil;
import com.fanyin.validation.annotation.RangeDouble;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 二哥很猛
 * @date 2018/8/14 11:43
 */
public class MobileDefine implements ConstraintValidator<RangeDouble,String> {

    /**
     * 是否必填
     */
    private boolean required;

    @Override
    public void initialize(RangeDouble constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (!required && value == null) || RegExpUtil.mobile(value);
    }
}
