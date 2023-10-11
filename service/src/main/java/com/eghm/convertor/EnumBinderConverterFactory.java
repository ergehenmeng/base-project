package com.eghm.convertor;

import com.eghm.enums.EnumBinder;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;


/**
 * @author 二哥很猛
 * @since 2023/10/10
 */
@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
public class EnumBinderConverterFactory implements ConverterFactory<String, Enum> {

    @NonNull
    @Override
    public <T extends Enum> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
        return new EnumBinderConverter<>(targetType);
    }

    public static class EnumBinderConverter<T extends Enum> implements Converter<String, T> {

        private final Class<T> enumType;

        public EnumBinderConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(@NonNull String value) {
            if (value.isEmpty()) {
                return null;
            }
            if (EnumBinder.class.isAssignableFrom(enumType)) {
                T[] enums = enumType.getEnumConstants();
                int v = Integer.parseInt(value);
                for (T e : enums) {
                    EnumBinder binder = (EnumBinder) e;
                    if (v == binder.getValue()) {
                        return e;
                    }
                }
                log.info("枚举类型映射失败 [{}] [{}]", enumType, value);
                throw new BusinessException(ErrorCode.ENUMS_FORMAT);
            } else {
                return (T) Enum.valueOf(this.enumType, value.trim());
            }
        }

    }

}
