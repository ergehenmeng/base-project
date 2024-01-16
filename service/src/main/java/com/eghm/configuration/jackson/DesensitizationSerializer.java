package com.eghm.configuration.jackson;

import cn.hutool.core.util.DesensitizedUtil;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.FieldType;
import com.eghm.exception.BusinessException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author 二哥很猛
 * @since 2023/12/27
 */
@Slf4j
public class DesensitizationSerializer extends StdScalarSerializer<Object> {

    private final FieldType fieldType;

    public DesensitizationSerializer(FieldType fieldType) {
        super(String.class, false);
        this.fieldType = fieldType;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeString("");
            return;
        }
        try {
            DesensitizedUtil.DesensitizedType desensitizedType = DesensitizedUtil.DesensitizedType.valueOf(fieldType.name());
            gen.writeString(DesensitizedUtil.desensitized(value.toString(), desensitizedType));
        } catch (Exception e) {
            log.error("该字段类型不支持脱敏 [{}]", fieldType, e);
            throw new BusinessException(ErrorCode.FIELD_NOT_SUPPORTED);
        }
    }

}
