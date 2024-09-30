package com.eghm.convertor;

import com.eghm.utils.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 时间中文转换器
 *
 * @author 二哥很猛
 * @since 2023/9/16
 */
public class DateParseEncoder extends StdSerializer<LocalDateTime> {

    protected DateParseEncoder() {
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
            gen.writeString(DateUtil.chineseValue(value));
        }
    }
}
