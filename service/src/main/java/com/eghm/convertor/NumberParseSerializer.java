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
public class NumberParseSerializer extends StdSerializer<Long> {

    private static final int TEN = 10;

    private static final int HUNDRED = 100;

    private static final int THOUSAND = 1000;

    private static final int TEN_THOUSAND = 10000;

    protected NumberParseSerializer() {
        super(Long.class);
    }

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
            if (value < TEN) {
                gen.writeString(String.valueOf(value));
            } else if (value < HUNDRED) {
                gen.writeString((value / TEN) + "+");
            } else if (value < THOUSAND) {
                gen.writeString((value / HUNDRED) + "+");
            } else if (value < TEN_THOUSAND) {
                gen.writeString((value / THOUSAND) + "+");
            } else {
                gen.writeString(value / TEN_THOUSAND + "万+");
            }
        }
    }
}
