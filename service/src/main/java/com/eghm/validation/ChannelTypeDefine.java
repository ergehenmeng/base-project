package com.eghm.validation;

import cn.hutool.core.util.StrUtil;
import com.eghm.enums.Channel;
import com.eghm.validation.annotation.ChannelType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author 二哥很猛
 * @since 2018/8/14 16:07
 */
public class ChannelTypeDefine implements ConstraintValidator<ChannelType, String> {

    private boolean required;

    private Channel[] channels;

    @Override
    public void initialize(ChannelType constraintAnnotation) {
        this.required = constraintAnnotation.required();
        this.channels = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!required && StrUtil.isBlank(value)) {
            return true;
        }
        for (Channel channel : channels) {
            if (channel.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
