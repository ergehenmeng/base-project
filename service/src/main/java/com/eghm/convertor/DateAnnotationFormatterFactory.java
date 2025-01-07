package com.eghm.convertor;

import com.eghm.annotation.DateFormatter;
import org.springframework.context.support.EmbeddedValueResolutionSupport;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.datetime.standard.DateTimeFormatterFactory;
import org.springframework.format.datetime.standard.TemporalAccessorPrinter;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
 * 日期格式化注解工厂, 主要解决LocalDateTime、LocalDate、LocalTime的GET请求时的数据绑定
 *
 * @author 二哥很猛
 * @since 2023/11/21
 */
public class DateAnnotationFormatterFactory extends EmbeddedValueResolutionSupport implements AnnotationFormatterFactory<DateFormatter> {

    private static final Set<Class<?>> FIELD_TYPES;

    static {
        FIELD_TYPES = Set.of(LocalDate.class, LocalTime.class, LocalDateTime.class);
    }

    @Override
    public final @NonNull Set<Class<?>> getFieldTypes() {
        return FIELD_TYPES;
    }

    @Override
    public @NonNull Printer<?> getPrinter(@NonNull DateFormatter annotation, @NonNull Class<?> fieldType) {
        DateTimeFormatter formatter = getFormatter(annotation);
        return new TemporalAccessorPrinter(formatter);
    }

    @Override
    public @NonNull Parser<?> getParser(@NonNull DateFormatter annotation, @NonNull Class<?> fieldType) {
        DateTimeFormatter formatter = getFormatter(annotation);
        return new DateFormatterParser(fieldType, formatter, annotation.offset(), annotation.unit());
    }

    /**
     * Factory method used to create a {@link DateTimeFormatter}.
     *
     * @param annotation the format annotation for the field
     * @return a {@link DateTimeFormatter} instance
     */
    protected DateTimeFormatter getFormatter(DateFormatter annotation) {
        DateTimeFormatterFactory factory = new DateTimeFormatterFactory();
        String pattern = resolveEmbeddedValue(annotation.pattern());
        if (StringUtils.hasLength(pattern)) {
            factory.setPattern(pattern);
        }
        return factory.createDateTimeFormatter();
    }

}
