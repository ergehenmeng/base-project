package com.eghm.convertor;

import com.eghm.annotation.JsonDesc;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 二哥很猛
 * @since 2025/1/9
 */
@Slf4j
public class EnumDescSerializer extends JsonSerializer<Enum<?>> {

    private static final Map<Class<?>, Method> JSON_VALUE_MAP = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Method> JSON_DESC_MAP = new ConcurrentHashMap<>();

    @Override
    public void serialize(Enum<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Method valueMethod = getJsonValueMethod(value.getClass(), JSON_VALUE_MAP, JsonValue.class);
        if (valueMethod == null) {
            throw new IllegalArgumentException("Enum class " + value.getClass().getName() + " must have a method annotated with @JsonValue");
        }
        Method descMethod = getJsonValueMethod(value.getClass(), JSON_DESC_MAP, JsonDesc.class);
        if (descMethod == null) {
            throw new IllegalArgumentException("Enum class " + value.getClass().getName() + " must have a method annotated with @JsonValue");
        }
        gen.writeStartObject();
        try {
            gen.writeStringField(gen.getOutputContext().getCurrentName(), descMethod.invoke(value).toString());
            gen.writeStringField(gen.getOutputContext().getCurrentName() + "Name", valueMethod.invoke(value).toString());
        } catch (Exception e) {
            log.error("枚举转换异常", e);
            throw new BusinessException(ErrorCode.ENUMS_FORMAT);
        }
        gen.writeEndObject();
    }

    private static Method getJsonValueMethod(Class<?> clazz, Map<Class<?>, Method> jsonMap, Class<? extends Annotation> annotationClass) {
        return jsonMap.computeIfAbsent(clazz, als -> {
            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(annotationClass)) {
                    return method;
                }
            }
            return null;
        });
    }
}
