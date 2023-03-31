package com.eghm.convertor;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.eghm.enums.excel.ExcelEnum;
import org.springframework.stereotype.Component;

/**
 * easy excel转化枚举类
 * @author wyb
 * @since 2023/3/30
 */
@Component
public class EnumConverter implements Converter<ExcelEnum> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return ExcelEnum.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(ExcelEnum value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>(value.getValue());
    }
}
