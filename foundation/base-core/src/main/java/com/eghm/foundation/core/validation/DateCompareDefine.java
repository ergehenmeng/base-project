package com.eghm.foundation.core.validation;

import com.eghm.foundation.core.dto.ext.LocalDateCompare;
import com.eghm.foundation.core.validation.annotation.DateCompare;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.temporal.ChronoUnit;

/**
 * @author 二哥很猛
 * @since 2023/10/19
 */
public class DateCompareDefine implements ConstraintValidator<DateCompare, LocalDateCompare> {

    @Override
    public boolean isValid(LocalDateCompare value, ConstraintValidatorContext context) {
        if (value == null || value.getStartDate() == null || value.getEndDate() == null) {
            return true;
        }
        return ChronoUnit.DAYS.between(value.getStartDate(), value.getEndDate()) >= 0;
    }
}
