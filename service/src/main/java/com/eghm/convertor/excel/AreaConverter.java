package com.eghm.convertor.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;
import com.eghm.cache.CacheProxyService;
import com.eghm.model.SysArea;
import com.eghm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 替换原生的Integer转换器
 *
 * @author wyb
 * @since 2023/3/31
 */
@Slf4j
public class AreaConverter implements Converter<Long> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Long.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Long value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new WriteCellData<>("");
        }
        CacheProxyService service = SpringContextUtil.getBean(CacheProxyService.class);
        SysArea dictValue = service.getAreaById(value);
        if (dictValue == null) {
            log.warn("导出Excel解析区域信息为空 [{}]", value);
            return NumberUtils.formatToCellDataString(value, contentProperty);
        }
        return new WriteCellData<>(dictValue.getTitle());
    }
}
