package com.eghm.convertor;

import cn.hutool.core.net.Ipv4Util;
import com.eghm.exception.BusinessException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;

import static com.eghm.enums.ErrorCode.IP_ILLEGAL;
import static com.eghm.utils.StringUtil.isBlank;

/**
 * ip反序列化器
 *
 * @author 二哥很猛
 * @since 2023/8/1
 */
public class IpToLongDeserializer extends StdScalarDeserializer<Long> {

    protected IpToLongDeserializer() {
        super(Long.class);
    }

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String text = p.getText().trim();
        if (isBlank(text)) {
            return null;
        }
        try {
            return Ipv4Util.ipv4ToLong(text);
        } catch (Exception e) {
            throw new BusinessException(IP_ILLEGAL);
        }
    }
}
