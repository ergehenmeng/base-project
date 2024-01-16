package com.eghm.convertor.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.eghm.utils.DecimalUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * excel导出金额转换器
 *
 * @author wyb
 * @since 2023/3/30
 */
@Slf4j
public class CentToYuanConverter implements Converter<Integer> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new WriteCellData<>("");
        }
        return new WriteCellData<>(DecimalUtil.centToYuan(value));
    }

}
