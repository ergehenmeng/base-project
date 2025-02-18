package com.eghm.validation;

import com.eghm.validation.annotation.AfterNow;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/19
 */
public class AfterNowDefine implements ConstraintValidator<AfterNow, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !LocalDate.now().isAfter(value);
    }
}
