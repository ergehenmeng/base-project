package com.eghm.convertor;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.eghm.annotation.ExcelValue;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * easy excel转化枚举类
 * @author wyb
 * @since 2023/3/30
 */
@Slf4j
public class EnumConverter implements Converter<Object> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Object.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (!(value instanceof Enum)) {
            log.error("该类型不是枚举不支持使用EnumConverter转换器 [{}]", value.getClass());
            throw new BusinessException(ErrorCode.ENUM_SUPPORTED);
        }
        Field valueAs = null;
        for (Field field : contentProperty.getField().getType().getDeclaredFields()) {
            ExcelValue excelValue = field.getAnnotation(ExcelValue.class);
            if (excelValue != null) {
                valueAs = field;
                break;
            }
        }
        if (valueAs == null) {
            log.error("枚举类请使用@ExcelValue标注解释字段 [{}]", value.getClass());
            throw new BusinessException(ErrorCode.ENUM_SUPPORTED);
        }
        return new WriteCellData<>(ReflectUtil.getFieldValue(value, valueAs).toString());
    }
}
