package com.eghm.validation;

import cn.hutool.core.util.StrUtil;
import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.SensitiveUtil;
import com.eghm.validation.annotation.WordChecker;
import lombok.extern.slf4j.Slf4j;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 校验规则定义
 *
 * @author 二哥很猛
 * @since 2018/8/14 11:04
 */
@Slf4j
public class WordCheckerDefine implements ConstraintValidator<WordChecker, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isBlank(value)) {
            return true;
        }
        FoundWord sensitive = SensitiveUtil.getFoundFirstSensitive(value);
        if (sensitive == null) {
            return true;
        }
        log.warn("检测到敏感词：[{}]", sensitive.getFoundWord());
        return false;
    }
}
