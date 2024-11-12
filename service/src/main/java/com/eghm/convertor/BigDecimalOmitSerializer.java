package com.eghm.convertor;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * BigDecimal转换字符串,不保留小数位的最后的0
 *
 * @author 二哥很猛
 * @since 2024/7/6
 */
public class BigDecimalOmitSerializer extends StdSerializer<Object> {

    public BigDecimalOmitSerializer() {
        super(Object.class);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value instanceof BigDecimal) {
            BigDecimal val = (BigDecimal) value;
            gen.writeString(val.stripTrailingZeros().toPlainString());
        } else {
            throw new BusinessException(ErrorCode.CONVERT_ERROR);
        }
    }

}