package com.eghm.convertor;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.utils.DecimalUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * 分转元序列化转换器(忽略无用的小数点)
 *
 * @author 二哥很猛
 * @since 2022/3/30 18:17
 */
public class CentToYuanOmitSerializer extends StdSerializer<Object> {

    public CentToYuanOmitSerializer() {
        super(Object.class);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value instanceof Integer) {
            int val = (int) value;
            if (val == 0) {
                gen.writeString("0");
            } else {
                gen.writeString(DecimalUtil.centToYuanOmit(val));
            }
        } else {
            throw new BusinessException(ErrorCode.CONVERT_ERROR);
        }
    }

}
