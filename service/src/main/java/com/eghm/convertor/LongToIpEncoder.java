package com.eghm.convertor;

import cn.hutool.core.net.Ipv4Util;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author 二哥很猛
 * @since 2023/8/1
 */
public class LongToIpEncoder extends StdSerializer<Long> {

    protected LongToIpEncoder() {
        super(Long.class);
    }

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeString("");
        } else {
            gen.writeString(Ipv4Util.longToIpv4(value));
        }
    }
}
