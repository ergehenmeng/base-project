package com.eghm.convertor;

import cn.hutool.core.util.ReflectUtil;
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
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 二哥很猛
 * @since 2025/1/9
 */
@Slf4j
public class EnumDescSerializer extends JsonSerializer<Enum<?>> {

    private static final Map<Class<?>, Field> JSON_VALUE_MAP = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Field> JSON_DESC_MAP = new ConcurrentHashMap<>();

    @Override
    public void serialize(Enum<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Field valueField = getJsonValueMethod(value.getClass(), JSON_VALUE_MAP, JsonValue.class);
        if (valueField == null) {
            throw new IllegalArgumentException("Enum class " + value.getClass().getName() + " must have a method annotated with @JsonValue");
        }
        Field descField = getJsonValueMethod(value.getClass(), JSON_DESC_MAP, JsonDesc.class);
        if (descField == null) {
            throw new IllegalArgumentException("Enum class " + value.getClass().getName() + " must have a method annotated with @JsonValue");
        }
        gen.writeStartObject();
        try {
            gen.writeObjectField("value", ReflectUtil.getFieldValue(value, valueField));
            gen.writeStringField("name", ReflectUtil.getFieldValue(value, descField).toString());
        } catch (Exception e) {
            log.error("枚举转换异常", e);
            throw new BusinessException(ErrorCode.ENUMS_FORMAT);
        }
        gen.writeEndObject();
    }

    private static Field getJsonValueMethod(Class<?> clazz, Map<Class<?>, Field> jsonMap, Class<? extends Annotation> annotationClass) {
        return jsonMap.computeIfAbsent(clazz, als -> {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(annotationClass)) {
                    return field;
                }
            }
            return null;
        });
    }
}
