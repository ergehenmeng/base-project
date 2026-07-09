package com.eghm.interfaces.core.configuration.jackson;

import com.eghm.domain.shared.utils.EnumValueUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Writes domain enums by value/code without Jackson annotations in domain.
 */
public class DomainEnumJsonSerializer extends JsonSerializer<Enum<?>> {

    @Override
    public void serialize(Enum<?> value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        generator.writeObject(EnumValueUtil.value(value));
    }
}
