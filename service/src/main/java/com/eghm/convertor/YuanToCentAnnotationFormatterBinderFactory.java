package com.eghm.convertor;

import com.eghm.annotation.YuanToCentFormat;
import com.eghm.utils.DecimalUtil;
import org.springframework.context.support.EmbeddedValueResolutionSupport;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author 二哥很猛
 * @since 2023/10/8
 */
public class YuanToCentAnnotationFormatterBinderFactory extends EmbeddedValueResolutionSupport implements AnnotationFormatterFactory<YuanToCentFormat> {

    private static final Set<Class<?>> FIELD_TYPES;

    static {
        Set<Class<?>> fieldTypes = new HashSet<>(4);
        fieldTypes.add(Integer.class);
        fieldTypes.add(Long.class);
        FIELD_TYPES = Collections.unmodifiableSet(fieldTypes);
    }

    @Override
    @NonNull
    public Set<Class<?>> getFieldTypes() {
        return FIELD_TYPES;
    }

    @Override
    @NonNull
    public Printer<?> getPrinter(@NonNull YuanToCentFormat annotation, @NonNull Class<?> fieldType) {
        return new YuanToCentFormatter();
    }

    @NonNull
    @Override
    public Parser<?> getParser(@NonNull YuanToCentFormat annotation, @NonNull Class<?> fieldType) {
        return new YuanToCentFormatter();
    }

    public static class YuanToCentFormatter implements Formatter<Integer> {

        @NonNull
        @Override
        public Integer parse(@NonNull String text, @NonNull Locale locale) {
            double value = new BigDecimal(text.trim()).doubleValue();
            return DecimalUtil.yuanToCent(value);
        }

        @NonNull
        @Override
        public String print(@NonNull Integer value, @NonNull Locale locale) {
            return DecimalUtil.centToYuan(value);
        }
    }
}
