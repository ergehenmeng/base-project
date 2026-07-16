package com.eghm.foundation.core.validation;


import com.eghm.foundation.core.utils.RegExpUtil;
import com.eghm.foundation.core.validation.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.eghm.foundation.core.utils.StringUtil.isBlank;
import static com.eghm.foundation.core.utils.StringUtil.isNotBlank;

/**
 * @author 二哥很猛
 * @since 2023/12/14 19:30
 */
public class PasswordDefine implements ConstraintValidator<Password, String> {

    /**
     * 是否必填
     */
    private boolean required;

    @Override
    public void initialize(Password constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (!required && isBlank(value)) || (isNotBlank(value) && RegExpUtil.password(value));
    }

}
