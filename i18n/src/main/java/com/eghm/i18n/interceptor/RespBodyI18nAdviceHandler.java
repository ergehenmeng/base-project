package com.eghm.i18n.interceptor;

import cn.hutool.core.util.NumberUtil;
import com.eghm.i18n.annotation.I18nMapper;
import com.eghm.i18n.context.LanguageContextHolder;
import com.eghm.i18n.provider.I18nMessageProvider;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对返回前端的对象进行国际化翻译
 * 支持方式： 使用 @I18nMapper 注解
 *
 * @author wyb-eghm
 * @since 2026/6/25
 */
@Slf4j
@AllArgsConstructor
public class RespBodyI18nAdviceHandler implements ResponseBodyAdvice<Object> {
    
    private I18nMessageProvider messageProvider;
    
    private final Map<Class<?>, Field[]> fieldCache = new ConcurrentHashMap<>();
    
    @Override
    public boolean supports(@Nonnull MethodParameter returnType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        if (messageProvider == null) {
            return false;
        }
        Class<?> parameterType = returnType.getParameterType();
        return parameterType.isAnnotationPresent(I18nMapper.class);
    }
    
    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object body, @Nonnull MethodParameter returnType, 
            @Nonnull MediaType selectedContentType, @Nonnull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @Nonnull ServerHttpRequest request, @Nonnull ServerHttpResponse response) {
        if (body == null) {
            return null;
        }
        Class<?> clazz = body.getClass();
        if (clazz.isAnnotationPresent(I18nMapper.class)) {
            return this.translateResponse(body, clazz.getAnnotation(I18nMapper.class));
        }
        return body;
    }
    
    /**
     * 对返回前端的对象中的错误码进行国际化翻译
     *
     * @param body body
     * @param annotation 注释
     * @return 响应对象
     */
    private Object translateResponse(Object body, I18nMapper annotation) {
        try {
            String codeName = annotation.code();
            String msgName = annotation.msg();
            int successCode = annotation.success();
            String key = annotation.key();
            Class<?> clazz = body.getClass();
            Field[] fields = this.getMapperField(clazz, codeName, msgName);
            if (fields == null) {
                return body;
            }
            Field codeField = fields[0];
            Object codeValue = ReflectionUtils.getField(codeField, body);
            if (codeValue == null || NumberUtil.parseInt(codeValue.toString()) == successCode) {
                return body;
            }
            Locale locale = LanguageContextHolder.getLocale();
            String translatedMsg = messageProvider.getMessage(codeValue.toString(), locale, key);
            ReflectionUtils.setField(fields[1], body, translatedMsg);
        } catch (Exception e) {
            log.error("I18nMapper-Field映射失败", e);
        }
        return body;
    }
    
    /**
     * 获取对象的 code 字段和 msg 字段
     *
     * @param clazz cls
     * @param codeName code
     * @param msgName name
     * @return filed
     */
    private Field[] getMapperField(Class<?> clazz, String codeName, String msgName) {
        return fieldCache.computeIfAbsent(clazz, k -> {
            Field codeField = ReflectionUtils.findField(k, codeName);
            Field msgField = ReflectionUtils.findField(k, msgName);
            if (codeField == null || msgField == null) {
                return null;
            }
            ReflectionUtils.makeAccessible(codeField);
            ReflectionUtils.makeAccessible(msgField);
            return new Field[]{codeField, msgField};
        });
    }
}