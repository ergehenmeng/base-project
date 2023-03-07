package com.eghm.utils;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

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
public class BeanValidator {
    
    private static ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().addProperty(HibernateValidatorConfiguration.FAIL_FAST, "true").buildValidatorFactory();
    
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
