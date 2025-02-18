package com.eghm.validation;


import cn.hutool.core.util.IdcardUtil;
import com.eghm.validation.annotation.IdCard;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.eghm.utils.StringUtil.isBlank;
import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * @author 二哥很猛
 * @since 2018/8/14 11:59
 */
public class IdCardDefine implements ConstraintValidator<IdCard, String> {

    /**
     * 是否必填
     */
    private boolean required;

    @Override
    public void initialize(IdCard constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (!required && isBlank(value)) || (isNotBlank(value) && IdcardUtil.isValidCard18(value));
    }
}
