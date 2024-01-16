package com.eghm.convertor.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;
import com.eghm.annotation.ExcelDict;
import com.eghm.service.sys.SysDictService;
import com.eghm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 替换原生的Integer转换器
 *
 * @author wyb
 * @since 2023/3/31
 */
@Slf4j
public class DictConverter implements Converter<Integer> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        ExcelDict dict = contentProperty.getField().getAnnotation(ExcelDict.class);
        if (dict == null) {
            return NumberUtils.formatToCellDataString(value, contentProperty);
        }
        SysDictService service = SpringContextUtil.getBean(SysDictService.class);
        String dictValue = service.getDictValue(dict.value(), value);
        if (dictValue == null) {
            log.warn("导出Excel解析数据字典为空 [{}] [{}]", dict.value(), value);
            return NumberUtils.formatToCellDataString(value, contentProperty);
        }
        return new WriteCellData<>(dictValue);
    }
}
