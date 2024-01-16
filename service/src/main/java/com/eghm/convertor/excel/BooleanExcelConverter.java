package com.eghm.convertor.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;

/**
 * easy excel转化枚举类
 *
 * @author wyb
 * @since 2023/3/30
 */
@Slf4j
public class BooleanExcelConverter implements Converter<Boolean> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Boolean.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Boolean value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new WriteCellData<>("");
        }
        if (Boolean.TRUE.equals(value)) {
            return new WriteCellData<>("是");
        }
        return new WriteCellData<>("否");
    }

}
