package com.eghm.foundation.core.convertor;

import com.eghm.foundation.core.annotation.YuanToCentFormat;
import com.eghm.foundation.core.utils.DecimalUtil;
import jakarta.annotation.Nonnull;
import org.springframework.context.support.EmbeddedValueResolutionSupport;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Set;

/**
 * @author 二哥很猛
 * @since 2023/10/8
 */
public class YuanToCentAnnotationFormatterBinderFactory extends EmbeddedValueResolutionSupport implements AnnotationFormatterFactory<YuanToCentFormat> {

    private static final Set<Class<?>> FIELD_TYPES;

    static {
        FIELD_TYPES = Set.of(Integer.class, Long.class);
    }

    @Override
    @Nonnull
    public Set<Class<?>> getFieldTypes() {
        return FIELD_TYPES;
    }

    @Override
    @Nonnull
    public Printer<?> getPrinter(@Nonnull YuanToCentFormat annotation, @Nonnull Class<?> fieldType) {
        return new YuanToCentFormatter();
    }

    @Nonnull
    @Override
    public Parser<?> getParser(@Nonnull YuanToCentFormat annotation, @Nonnull Class<?> fieldType) {
        return new YuanToCentFormatter();
    }

    public static class YuanToCentFormatter implements Formatter<Integer> {

        @Nonnull
        @Override
        public Integer parse(@Nonnull String text, @Nonnull Locale locale) {
            double value = new BigDecimal(text.trim()).doubleValue();
            return DecimalUtil.yuanToCent(value);
        }

        @Nonnull
        @Override
        public String print(@Nonnull Integer value, @Nonnull Locale locale) {
            return DecimalUtil.centToYuan(value);
        }
    }
}
