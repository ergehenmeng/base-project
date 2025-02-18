package com.eghm.validation;


import cn.hutool.core.util.PhoneUtil;
import com.eghm.validation.annotation.Mobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.eghm.utils.StringUtil.isBlank;

/**
 * @author 二哥很猛
 * @since 2018/8/14 11:43
 */
public class MobileDefine implements ConstraintValidator<Mobile, String> {

    /**
     * 是否必填
     */
    private boolean required;

    @Override
    public void initialize(Mobile constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (!required && isBlank(value)) || PhoneUtil.isMobile(value);
    }
}
