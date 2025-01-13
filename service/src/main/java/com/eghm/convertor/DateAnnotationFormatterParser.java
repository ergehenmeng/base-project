package com.eghm.convertor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Parser;
import org.springframework.format.datetime.standard.DateTimeContextHolder;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Locale;

/**
 * @author 二哥很猛
 * @since 2023/11/21
 */
@Slf4j
public class DateAnnotationFormatterParser implements Parser<Temporal> {

    private final long offset;

    private final ChronoUnit unit;

    private final DateTimeFormatter formatter;

    private final Class<?> temporalAccessorType;

    public DateAnnotationFormatterParser(Class<?> temporalAccessorType, DateTimeFormatter formatter, long offset, ChronoUnit unit) {
        this.temporalAccessorType = temporalAccessorType;
        this.formatter = formatter;
        this.offset = offset;
        this.unit = unit;
    }

    @Override
    public @NonNull Temporal parse(@NonNull String text, @NonNull Locale locale) {
        try {
            Temporal parse = this.doParse(text, locale, this.formatter);
            return parse.plus(this.offset, this.unit);
        } catch (DateTimeParseException ex) {
            log.info("日期参数解析失败 [{}]", text, ex);
            throw ex;
        }
    }

    private Temporal doParse(String text, Locale locale, DateTimeFormatter formatter) throws DateTimeParseException {
        DateTimeFormatter formatterToUse = DateTimeContextHolder.getFormatter(formatter, locale);
        if (LocalDate.class == this.temporalAccessorType) {
            return LocalDate.parse(text, formatterToUse);
        } else if (LocalTime.class == this.temporalAccessorType) {
            return LocalTime.parse(text, formatterToUse);
        } else if (LocalDateTime.class == this.temporalAccessorType) {
            return LocalDateTime.parse(text, formatterToUse);
        } else {
            throw new IllegalStateException("Unsupported TemporalAccessor type: " + this.temporalAccessorType);
        }
    }
}
