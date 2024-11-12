package com.eghm.convertor;

import cn.hutool.core.util.StrUtil;
import com.eghm.enums.EnumBinder;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ValueEnumBinder;
import com.eghm.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;


/**
 * 枚举类型转换器工厂,解决knife4j-doc枚举类型显示异常及请求接口时类型绑定异常,注意该转换类主要针对GET请求, post默认采用jackson中JsonCreator绑定
 *
 * @author 二哥很猛
 * @since 2023/10/10
 */
@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
public class Knife4jEnumBinderConverterFactory implements ConverterFactory<String, Enum> {

    @NonNull
    @Override
    public <T extends Enum> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
        return new EnumBinderConverter<>(targetType);
    }

    public static class EnumBinderConverter<T extends Enum> implements Converter<String, T> {

        private final Class<T> enumType;

        public EnumBinderConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(@NonNull String value) {
            if (StrUtil.isBlank(value)) {
                return null;
            }
            // knife4j-doc枚举类型是通过toString展示的, 因此如果正常展示需要重写枚举的toString方法,即: value + ":" + desc,
            // 同时调试入参也是通过toString作为参数传递给后端, 下面这句代码仅仅为了兼容knife4j页面调试
            value = value.split(":")[0];
            if (EnumBinder.class.isAssignableFrom(enumType)) {
                T[] enums = enumType.getEnumConstants();
                int v = Integer.parseInt(value);
                for (T e : enums) {
                    EnumBinder binder = (EnumBinder) e;
                    if (v == binder.getValue()) {
                        return e;
                    }
                }
                log.info("数字枚举类型映射失败 [{}] [{}]", enumType, value);
                throw new BusinessException(ErrorCode.ENUMS_FORMAT);
            } else if (ValueEnumBinder.class.isAssignableFrom(enumType)) {
                T[] enums = enumType.getEnumConstants();
                for (T e : enums) {
                    ValueEnumBinder binder = (ValueEnumBinder) e;
                    if (value.equals(binder.getValue())) {
                        return e;
                    }
                }
                log.info("字符串枚举类型映射失败 [{}] [{}]", enumType, value);
                throw new BusinessException(ErrorCode.ENUMS_FORMAT);
            } else {
                return (T) Enum.valueOf(this.enumType, value.trim());
            }
        }
    }
}
