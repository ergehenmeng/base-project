package com.eghm.model.validation;


import com.eghm.common.utils.RegExpUtil;
import com.eghm.common.utils.StringUtil;
import com.eghm.model.validation.annotation.RangeDouble;

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
        return (!required && value == null) || (StringUtil.isNotBlank(value) && RegExpUtil.mobile(value));
    }
}
