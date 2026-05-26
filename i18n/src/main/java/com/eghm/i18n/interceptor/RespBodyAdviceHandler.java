package com.eghm.i18n.interceptor;

import com.eghm.i18n.context.LanguageContextHolder;
import com.eghm.i18n.provider.I18nMessageProvider;
import com.eghm.i18n.provider.RespBodyProvider;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Locale;

/**
 * 对返回前端的对象 code != success的错误码进行翻译
 *
 * @author wyb-eghm
 * @since 2026/5/21
 */
@AllArgsConstructor
public class RespBodyAdviceHandler implements ResponseBodyAdvice<RespBodyProvider> {
    
    private I18nMessageProvider messageProvider;
    
    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return messageProvider != null && RespBodyProvider.class.isAssignableFrom(returnType.getParameterType());
    }
    
    @Nullable
    @Override
    public RespBodyProvider beforeBodyWrite(@Nullable RespBodyProvider body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        if (body == null) {
            return null;
        }
        Integer successCode = body.successCode();
        Integer code = body.getCode();
        if (successCode != null && successCode.equals(code)) {
            return body;
        }
        String msg = body.getMsg();
        if (msg == null || msg.isEmpty()) {
            return body;
        }
        Locale locale = LanguageContextHolder.getLocale();
        String translatedMsg = messageProvider.getMessage(msg, locale, I18nMessageProvider.ERROR_CODE);
        body.setMsg(translatedMsg);
        return body;
    }
}