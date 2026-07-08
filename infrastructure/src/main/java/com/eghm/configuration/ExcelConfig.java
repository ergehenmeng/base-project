package com.eghm.configuration;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.converters.DefaultConverterLoader;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * 注册EasyExcel自定义转换器
 * 注意: 目前Converter的自定义实现类尚未被spring管理, 故此处会为空
 *
 * @author wyb
 * @since 2023/3/31
 */

@Configuration
public class ExcelConfig {

    public ExcelConfig(ObjectProvider<List<Converter<?>>> provider) {
        provider.ifAvailable(converters -> {
            Map<ConverterKeyBuild.ConverterKey, Converter<?>> writeConverter = DefaultConverterLoader.loadDefaultWriteConverter();
            for (Converter<?> converter : converters) {
                writeConverter.put(ConverterKeyBuild.buildKey(converter.supportJavaTypeKey()), converter);
            }
        });
    }
}
