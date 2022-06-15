package com.eghm.common.convertor;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.DecimalUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author wyb
 * @date 2022/3/30 18:17
 */
public class CentToYuanEncoder extends StdSerializer<Object> {

    public CentToYuanEncoder() {
        super(Object.class);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value instanceof Integer) {
            int val = (int)value;
            if (val == 0) {
                gen.writeString("0");
            } else {
                gen.writeString(DecimalUtil.centToYuan(val));
            }
        } else {
            throw new BusinessException(ErrorCode.CONVERT_ERROR);
        }
    }

}
