package com.eghm.convertor;

import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;

/**
 * @author 二哥很猛
 * @since 2023/8/1
 */
public class IpToLongDecoder extends StdScalarDeserializer<Long> {

    protected IpToLongDecoder() {
        super(Long.class);
    }

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText().trim();
        if (StrUtil.isEmpty(text)) {
            return null;
        }
        return Ipv4Util.ipv4ToLong(text);
    }
}
