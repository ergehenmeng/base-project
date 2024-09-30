package com.eghm.convertor.excel;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.eghm.annotation.ExcelDesc;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举转换器
 *
 * @author wyb
 * @since 2023/3/30
 */
@Slf4j
public class EnumExcelConverter implements Converter<Object> {

    private static final Map<Class<?>, Field> FIELD_MAP = new ConcurrentHashMap<>(32);

    @Override
    public Class<?> supportJavaTypeKey() {
        return Object.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (!(value instanceof Enum)) {
            log.error("该类型不是枚举不支持使用EnumConverter转换器 [{}]", value.getClass());
            throw new BusinessException(ErrorCode.ENUM_SUPPORTED);
        }
        Field valueAs = this.getAnnotationField(contentProperty);
        return new WriteCellData<>(ReflectUtil.getFieldValue(value, valueAs).toString());
    }

    /**
     * 获取带有@ExcelDesc注解的属性
     *
     * @param contentProperty 原导出excel的字段
     * @return Field
     */
    private Field getAnnotationField(ExcelContentProperty contentProperty) {
        Class<?> fieldType = contentProperty.getField().getType();
        return FIELD_MAP.computeIfAbsent(fieldType, aClass -> {
            for (Field field : contentProperty.getField().getType().getDeclaredFields()) {
                ExcelDesc excelDesc = field.getAnnotation(ExcelDesc.class);
                if (excelDesc != null) {
                    return field;
                }
            }
            log.error("枚举类请使用@ExcelDesc标注要导出为Excel的字段 [{}]", fieldType);
            throw new BusinessException(ErrorCode.ENUM_SUPPORTED);
        });
    }
}
