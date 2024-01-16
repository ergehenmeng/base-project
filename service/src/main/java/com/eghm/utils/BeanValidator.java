package com.eghm.utils;

import cn.hutool.core.collection.CollUtil;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 殿小二
 * @date 2023/3/6
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanValidator {

    private static final ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory();

    public static <T> void validate(T t, Class<?>... groups) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(t, groups);
        if (CollUtil.isNotEmpty(validate)) {
            Iterator<ConstraintViolation<T>> iterator = validate.iterator();
            if (iterator.hasNext()) {
                ConstraintViolation<T> next = iterator.next();
                throw new BusinessException(ErrorCode.PARAM_VERIFY_ERROR.getCode(), next.getMessage());
            }
        }
    }

    public static void validateList(Collection<?> collection) {
        for (Object o : collection) {
            validate(o);
        }
    }

}
