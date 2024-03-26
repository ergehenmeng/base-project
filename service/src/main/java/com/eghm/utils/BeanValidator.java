package com.eghm.utils;

import cn.hutool.core.collection.CollUtil;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author 殿小二
 * @since 2023/3/6
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanValidator {

    private static final ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory();


    /**
     * 验证参数是否合法
     *
     * @param t 参数对象
     * @param consumer 校验失败时 执行的逻辑
     * @param groups 指定group
     * @param <T> 参数类型
     */
    public static <T> void validate(T t, Consumer<String> consumer, Class<?>... groups) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(t, groups);
        if (CollUtil.isNotEmpty(validate)) {
            Iterator<ConstraintViolation<T>> iterator = validate.iterator();
            if (iterator.hasNext()) {
                consumer.accept(iterator.next().getMessage());
            }
        }
    }

    /**
     * 验证参数是否合法
     *
     * @param collection 集合
     */
    public static void validateList(Collection<?> collection) {
        for (Object o : collection) {
            validate(o);
        }
    }

    /**
     * 验证参数是否合法
     *
     * @param t 参数对象
     * @param groups 指定group
     * @param <T> 参数类型
     */
    public static <T> void validate(T t, Class<?>... groups) {
        validate(t, errorMsg -> {
            throw new BusinessException(ErrorCode.PARAM_VERIFY_ERROR.getCode(), errorMsg);
        }, groups);
    }
}
