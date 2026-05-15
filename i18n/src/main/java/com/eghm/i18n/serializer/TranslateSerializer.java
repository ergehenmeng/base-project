package com.eghm.i18n.serializer;

import com.eghm.i18n.annotation.Translate;
import com.eghm.i18n.context.LanguageContextHolder;
import com.eghm.i18n.provider.I18nMessageProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Locale;

/**
 * @author wyb-eghm
 * @since 2026/5/15
 */
public class TranslateSerializer extends StdSerializer<Object> implements ContextualSerializer {
    
    private volatile static I18nMessageProvider PROVIDER;
    
    public TranslateSerializer() {
        super(Object.class);
    }
    
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
            throws JsonMappingException {
        Translate annotation = property.getAnnotation(Translate.class);
        if (annotation == null) {
            return prov.findContentValueSerializer(property.getType(), property);
        }
        return this;
    }
    
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            if (PROVIDER == null) {
                gen.writeObject(value);
            } else {
                Locale locale = LanguageContextHolder.getLocale();
                String message = PROVIDER.getMessage(value.toString(), locale);
                gen.writeString(message);
            }
        }
    }
    
    public static void setMessageProvider(I18nMessageProvider messageProvider) {
        PROVIDER = messageProvider;
    }
}
