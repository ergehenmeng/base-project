package com.eghm.model.validation;

import com.eghm.model.validation.annotation.SelectionByte;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 二哥很猛
 * @date 2019/8/20 10:32
 */
public class SelectionByteDefine implements ConstraintValidator<SelectionByte,Byte> {

    /**
     * 取值列表
     */
    private byte[] values;

    /**
     * 是否必填
     */
    private boolean required;


    @Override
    public void initialize(SelectionByte constraintAnnotation) {
        this.values = constraintAnnotation.value();
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Byte value, ConstraintValidatorContext context) {
        if(!required && value == null){
            return true;
        }
        for (byte v : values){
            if(v == value){
                return true;
            }
        }
        return false;
    }
}
