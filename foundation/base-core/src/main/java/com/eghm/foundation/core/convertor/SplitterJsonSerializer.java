package com.eghm.foundation.core.convertor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import static com.eghm.foundation.core.constants.CommonConstant.COMMA;

/**
 * @author 二哥很猛
 * @since 2023/1/17
 */
public class SplitterJsonSerializer extends JsonSerializer<String> {

    private final String delimiter;

    public SplitterJsonSerializer() {
        this(COMMA);
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
            this.doWrite(gen, part);
        }
        gen.writeEndArray();
    }

    /**
     * 序列化
     *
     * @param gen gen
     * @param value value
     * @throws IOException e
     */
    protected void doWrite(JsonGenerator gen, String value) throws IOException {
        gen.writeString(value.trim());
    }
}
