package com.eghm.convertor;

import com.eghm.utils.DecimalUtil;
import com.eghm.utils.StringUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2022/3/30 18:44
 */
public class YuanToCentDeserializer extends StdScalarDeserializer<Integer> {

    protected YuanToCentDeserializer() {
        super(Integer.class);
    }

    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String text = p.getText().trim();
        if (StringUtil.isBlank(text)) {
            return null;
        }
        double value = new BigDecimal(text).doubleValue();
        return DecimalUtil.yuanToCent(value);
    }
}
