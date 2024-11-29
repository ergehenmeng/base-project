package com.eghm.convertor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * 特殊处理数字的序列化
 *
 * @author 二哥很猛
 * @since 2023/9/16
 */
public class NumberParseSerializer extends StdSerializer<Integer> {

    protected NumberParseSerializer() {
        super(Integer.class);
    }

    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
            if (value < 10) {
                gen.writeString(String.valueOf(value));
            } else if (value < 100) {
                gen.writeString((value / 10) + "+");
            } else if (value < 1000) {
                gen.writeString((value / 100) + "+");
            } else if (value < 10000) {
                gen.writeString((value / 1000) + "+");
            } else {
                gen.writeString(value / 10000 + "万+");
            }
        }
    }
}
