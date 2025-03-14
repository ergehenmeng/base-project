package com.eghm.convertor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author 二哥很猛
 * @since 2023/1/17
 */
public class SplitterJsonSerializer extends JsonSerializer<String> {

    private final String delimiter;

    public SplitterJsonSerializer() {
        this(",");
    }

    public SplitterJsonSerializer(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null || value.trim().isEmpty()) {
            gen.writeStartArray();
            gen.writeEndArray();
            return;
        }
        String[] parts = value.split(delimiter);
        gen.writeStartArray();
        for (String part : parts) {
            gen.writeString(part.trim());
        }
        gen.writeEndArray();
    }
}
