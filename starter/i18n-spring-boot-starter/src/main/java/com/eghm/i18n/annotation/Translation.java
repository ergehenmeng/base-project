package com.eghm.i18n.annotation;

import com.eghm.i18n.serializer.TranslationSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 功能: 对返回前端的VO属性进行国际化翻译
 *
 * @author wyb-eghm
 * @since 2026/5/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = TranslationSerializer.class)
public @interface Translation {
    
    /**
     * 数据字典的key
     *
     * @return key
     */
    String value();
}
