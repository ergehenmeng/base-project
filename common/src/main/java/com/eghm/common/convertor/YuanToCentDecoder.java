package com.eghm.common.convertor;

import cn.hutool.core.util.StrUtil;
import com.eghm.common.utils.DecimalUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author wyb
 * @date 2022/3/30 18:44
 */
public class YuanToCentDecoder extends StdScalarDeserializer<Integer> {

    protected YuanToCentDecoder() {
        super(Integer.class);
    }

    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String text = p.getText().trim();
        if (StrUtil.isEmpty(text)) {
            return null;
        }
        double value = new BigDecimal(text).doubleValue();
        return DecimalUtil.yuanToCent(value);
    }
}
