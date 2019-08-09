package com.fanyin.validation;

import com.fanyin.validation.annotation.SectionInt;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 二哥很猛
 * @date 2018/8/14 13:46
 */
public class SectionIntDefine implements ConstraintValidator<SectionInt,Integer> {

    /**
     * 取值列表
     */
    private int[] values;

    /**
     * 是否必填
     */
    private boolean required;

    @Override
    public void initialize(SectionInt constraintAnnotation) {
        this.values = constraintAnnotation.value();
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(!required && value == null){
            return true;
        }
        for (int v : values){
            if(v == value){
                return true;
            }
        }
        return false;
    }
}
