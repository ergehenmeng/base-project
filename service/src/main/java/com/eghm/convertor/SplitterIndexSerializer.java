package com.eghm.convertor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import static com.eghm.constants.CommonConstant.COMMA;

/**
 * 针对多图片字符串，只返回第一个图片地址
 * @author 二哥很猛
 * @since 2025-05-25
 */
public class SplitterIndexSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null || value.trim().isEmpty()) {
            gen.writeString("");
            return;
        }
        gen.writeString(value.split(COMMA)[0]);
    }

}
