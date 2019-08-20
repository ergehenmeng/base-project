package com.fanyin.model.validation;

import com.fanyin.common.utils.StringUtil;
import com.fanyin.model.validation.annotation.RangeLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验规则定义
 * @author 二哥很猛
 * @date 2018/8/14 11:30
 */
public class RangeLengthDefine implements ConstraintValidator<RangeLength,String> {

    /**
     * 最小长度
     */
    private int min;

    /**
     * 最大长度
     */
    private int max;

    /**
     * 是否必填
     */
    private boolean required;


    @Override
    public void initialize(RangeLength constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (!required && value == null) || (StringUtil.isNotBlank(value) && max >= value.length() && min <= value.length()) ;
    }
}
