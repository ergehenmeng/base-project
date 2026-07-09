package com.eghm.configuration.jackson;

import com.eghm.utils.EnumValueUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Reads domain enums by value/code without Jackson annotations in domain.
 */
public class DomainEnumJsonDeserializer extends JsonDeserializer<Enum<?>> {

    private final Class<? extends Enum> enumType;

    public DomainEnumJsonDeserializer(Class<? extends Enum> enumType) {
        this.enumType = enumType;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Enum<?> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        Object value;
        if (node.isInt()) {
            value = node.intValue();
        } else if (node.isLong()) {
            value = node.longValue();
        } else if (node.isTextual()) {
            value = node.textValue();
        } else {
            value = node.asText();
        }
        return EnumValueUtil.fromValue((Class) enumType, value);
    }
}
