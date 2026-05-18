package com.eghm.i18n.annotation;

import com.eghm.i18n.serializer.TranslateSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wyb-eghm
 * @since 2026/5/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = TranslateSerializer.class)
public @interface Translate {
    
    /**
     * 数据字典的key, 注意: 如果字段为空, 则直接使用返回值进行翻译
     *
     * @return key
     */
    String value() default "";
}
