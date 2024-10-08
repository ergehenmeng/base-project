package com.eghm.convertor.excel;


import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.eghm.constants.CommonConstant;
import com.eghm.utils.ResourceUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 图片转换器 注意:只支持单张图片
 *
 * @author wyb
 * @since 2023/3/31
 */
@Slf4j
public class ImageConverter implements Converter<String> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String[] split = value.split(CommonConstant.COMMA);
        byte[] readBytes = ResourceUtil.readFile(split[0]);
        return new WriteCellData<>(readBytes);
    }
}
