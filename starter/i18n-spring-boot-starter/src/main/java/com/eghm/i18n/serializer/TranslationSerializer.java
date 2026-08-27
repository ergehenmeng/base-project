package com.eghm.i18n.serializer;

import cn.hutool.core.util.ReflectUtil;
import com.eghm.i18n.annotation.Translation;
import com.eghm.i18n.context.LanguageContextHolder;
import com.eghm.i18n.provider.I18nMessageProvider;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 兼容jackson枚举序列化翻译
 *
 * @author wyb-eghm
 * @since 2026/5/15
 */
public class TranslationSerializer extends StdSerializer<Object> implements ContextualSerializer {
    
    private volatile static I18nMessageProvider PROVIDER;
    
    private Translation annotation;
    
    private static final Map<Class<?>, Field> JSON_VALUE_MAP = new ConcurrentHashMap<>();
    
    public TranslationSerializer() {
        super(Object.class);
    }
    
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
            throws JsonMappingException {
        Translation annotation = property.getAnnotation(Translation.class);
        if (annotation == null) {
            return prov.findContentValueSerializer(property.getType(), property);
        }
        this.annotation = annotation;
        return this;
    }
    
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            Object result = value;
            if (value.getClass().isEnum()) {
                Field valueField = getJsonValueMethod(value.getClass());
                result = valueField ==  null ? value : ReflectUtil.getFieldValue(value, valueField);
            }
            if (PROVIDER == null) {
                gen.writeObject(result);
            } else {
                Locale locale = LanguageContextHolder.getLocale();
                gen.writeString(PROVIDER.getMessage(result.toString(), locale, annotation.value()));
            }
        }
    }
    
    public static void setMessageProvider(I18nMessageProvider messageProvider) {
        PROVIDER = messageProvider;
    }
    
    private static Field getJsonValueMethod(Class<?> clazz) {
        return JSON_VALUE_MAP.computeIfAbsent(clazz, als -> {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(JsonValue.class)) {
                    return field;
                }
            }
            return null;
        });
    }
}
