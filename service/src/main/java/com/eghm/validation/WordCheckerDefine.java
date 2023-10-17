package com.eghm.validation;

import cn.hutool.core.util.StrUtil;
import cn.hutool.dfa.SensitiveUtil;
import com.eghm.validation.annotation.WordChecker;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验规则定义
 * @author 二哥很猛
 * @date 2018/8/14 11:04
 */
public class WordCheckerDefine implements ConstraintValidator<WordChecker, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isBlank(value)) {
            return true;
        }
        return !SensitiveUtil.containsSensitive(value);
    }
}
