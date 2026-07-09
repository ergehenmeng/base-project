package com.eghm.convertor;

import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.shared.utils.EnumValueUtil;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 枚举类型转换器工厂
 * 解决GET请求枚举按领域值绑定的问题.
 *
 * @author 二哥很猛
 * @since 2023/10/10
 */
@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
public class EnumBinderConverterFactory implements ConverterFactory<String, Enum> {

    private static final Map<Class<?>, EnumBinderConverter> CONVERTER_MAP = new ConcurrentHashMap<>(32);
    
    @Nonnull
    @Override
    public <T extends Enum> Converter<String, T> getConverter(@Nonnull Class<T> targetType) {
        return CONVERTER_MAP.computeIfAbsent(targetType, clazz -> new EnumBinderConverter<>(targetType));
    }

    public static class EnumBinderConverter<T extends Enum> implements Converter<String, T> {

        private final Class<T> enumType;

        public EnumBinderConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(@Nonnull String value) {
            if (value.isEmpty()) {
                return null;
            }
            T enumValue = (T) EnumValueUtil.fromValue((Class) enumType, value.trim());
            if (enumValue != null) {
                return enumValue;
            }
            log.error("枚举绑定异常, [{}] [{}]", value, enumType.getName());
            throw new BusinessException(ErrorCode.ENUMS_FORMAT);
        }
    }
}

