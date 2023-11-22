package com.eghm.validation;

import com.eghm.dto.ext.LocalDateCompare;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.temporal.ChronoUnit;

/**
 * @author 二哥很猛
 * @since 2023/10/19
 */
public class DateCompareDefine implements ConstraintValidator<com.eghm.validation.annotation.DateCompare, LocalDateCompare> {

    @Override
    public boolean isValid(LocalDateCompare value, ConstraintValidatorContext context) {
        if (value == null || value.getStartDate() == null || value.getEndDate() == null) {
            return true;
        }
        return ChronoUnit.DAYS.between(value.getStartDate(), value.getEndDate()) >= 0;
    }
}
