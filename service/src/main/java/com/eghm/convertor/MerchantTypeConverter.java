package com.eghm.convertor;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.eghm.enums.MerchantType;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @since 2023/12/9
 */
public class MerchantTypeConverter implements Converter<Integer> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new WriteCellData<>("");
        }
        String merchantName = Arrays.stream(MerchantType.values()).filter(map -> (map.getValue() & value) == map.getValue()).map(MerchantType::getName).collect(Collectors.joining(","));
        return new WriteCellData<>(merchantName);
    }

}