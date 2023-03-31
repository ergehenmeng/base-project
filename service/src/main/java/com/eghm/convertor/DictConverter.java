package com.eghm.convertor;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;
import com.eghm.annotation.ExcelDict;
import com.eghm.utils.SpringContextUtil;

/**
 * @author wyb
 * @since 2023/3/31
 */
public class DictConverter implements Converter<Integer> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        ExcelDict dict = contentProperty.getField().getAnnotation(ExcelDict.class);
        if (dict == null) {
            return NumberUtils.formatToCellDataString(value, contentProperty);
        }

        SpringContextUtil.getBean("");
        return Converter.super.convertToExcelData(value, contentProperty, globalConfiguration);
    }
}
