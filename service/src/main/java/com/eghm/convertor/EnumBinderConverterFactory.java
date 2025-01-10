package com.eghm.convertor;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 枚举类型转换器工厂
 * 由于post默认采用jackson中JsonCreator绑定,此转换器是用来解决GET请求时的数据绑定问题,同样使用JsonCreator绑定
 *
 * @author 二哥很猛
 * @since 2023/10/10
 */
@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
public class EnumBinderConverterFactory implements ConverterFactory<String, Enum> {

    private static final Map<Class<?>, EnumBinderConverter> CONVERTER_MAP = new ConcurrentHashMap<>(32);

    @NonNull
    @Override
    public <T extends Enum> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
        return CONVERTER_MAP.computeIfAbsent(targetType, clazz -> new EnumBinderConverter<>(targetType));
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
            Method[] methods = enumType.getDeclaredMethods();
            for (Method method : methods) {
                JsonCreator creator = method.getDeclaredAnnotation(JsonCreator.class);
                if (creator != null && method.getParameterCount() == 1 && (method.getParameterTypes()[0] == String.class || method.getParameterTypes()[0] == Integer.class)) {
                    try {
                        return (T) method.invoke(null, value);
                    } catch (Exception e) {
                        log.info("枚举类型映射失败 [{}] [{}]", enumType, value);
                        throw new BusinessException(ErrorCode.ENUMS_FORMAT);
                    }
                }
            }
            return (T) Enum.valueOf(this.enumType, value.trim());
        }
    }
}

