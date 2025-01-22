package com.eghm.convertor;

import com.eghm.utils.DecimalUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

import static com.eghm.utils.StringUtil.isBlank;

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
        if (isBlank(text)) {
            return null;
        }
        double value = new BigDecimal(text).doubleValue();
        return DecimalUtil.yuanToCent(value);
    }
}
