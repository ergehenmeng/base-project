package com.eghm.platform.config.convertor;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;
import com.eghm.foundation.core.annotation.ExcelDict;
import com.eghm.platform.config.service.SysDictService;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据字典转换器
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
        SysDictService service = SpringUtil.getBean(SysDictService.class);
        String dictValue = service.getDictValue(dict.value(), value);
        if (dictValue == null) {
            log.warn("导出Excel解析数据字典为空 [{}] [{}]", dict.value(), value);
            return NumberUtils.formatToCellDataString(value, contentProperty);
        }
        return new WriteCellData<>(dictValue);
    }
}
