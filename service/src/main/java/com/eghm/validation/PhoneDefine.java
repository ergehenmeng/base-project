package com.eghm.validation;


import cn.hutool.core.util.PhoneUtil;
import static com.eghm.utils.StringUtil.isBlank;
import com.eghm.validation.annotation.Phone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author 二哥很猛
 * @since 2018/8/14 11:43
 */
public class PhoneDefine implements ConstraintValidator<Phone, String> {

    /**
     * 是否必填
     */
    private boolean required;

    @Override
    public void initialize(Phone constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (!required && isBlank(value)) || (PhoneUtil.isMobile(value) || PhoneUtil.isTel(value) || PhoneUtil.isTel400800(value));
    }
}
