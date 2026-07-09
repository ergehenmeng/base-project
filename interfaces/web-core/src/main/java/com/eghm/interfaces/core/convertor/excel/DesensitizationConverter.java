package com.eghm.interfaces.core.convertor.excel;

import cn.hutool.core.util.DesensitizedUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.eghm.application.shared.annotation.Desensitization;

/**
 * 对导出字段进行脱敏处理
 *
 * @author 二哥很猛
 * @since 2025/7/4
 */
public class DesensitizationConverter implements Converter<String> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new WriteCellData<>("");
        }
        Desensitization desensitization = contentProperty.getField().getAnnotation(Desensitization.class);
        if (desensitization == null) {
            return new WriteCellData<>(value);
        }
        DesensitizedUtil.DesensitizedType desensitizedType = DesensitizedUtil.DesensitizedType.valueOf(desensitization.value().name());
        return new WriteCellData<>(DesensitizedUtil.desensitized(value, desensitizedType));
    }
}
