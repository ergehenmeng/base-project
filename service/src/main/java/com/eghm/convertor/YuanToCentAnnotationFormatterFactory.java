package com.eghm.convertor;

import com.eghm.configuration.annotation.YuanToCenterFormat;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.EmbeddedValueResolutionSupport;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 二哥很猛
 * @since 2023/10/8
 */
public class YuanToCentAnnotationFormatterFactory extends EmbeddedValueResolutionSupport implements AnnotationFormatterFactory<YuanToCenterFormat> {

    private static final Set<Class<?>> FIELD_TYPES;

    static {
        Set<Class<?>> fieldTypes = new HashSet<>(4);
        fieldTypes.add(Integer.class);
        fieldTypes.add(Long.class);
        FIELD_TYPES = Collections.unmodifiableSet(fieldTypes);
    }


    @NotNull
    @Override
    public Set<Class<?>> getFieldTypes() {
        return FIELD_TYPES;
    }

    @Override
    @NonNull
    public Printer<?> getPrinter(@NonNull YuanToCenterFormat annotation, @NonNull Class<?> fieldType) {
        return new YuanToCentFormatter();
    }

    @NonNull
    @Override
    public Parser<?> getParser(@NonNull YuanToCenterFormat annotation, @NonNull Class<?> fieldType) {
        return new YuanToCentFormatter();
    }
}
