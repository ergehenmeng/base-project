package com.eghm.i18n.interceptor;

import com.eghm.i18n.context.LanguageContextHolder;
import com.eghm.i18n.provider.I18nMessageProvider;
import com.eghm.i18n.provider.RespBodyProvider;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
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
    public boolean supports(@Nonnull MethodParameter returnType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        return messageProvider != null && RespBodyProvider.class.isAssignableFrom(returnType.getParameterType());
    }
    
    @Override
    public RespBodyProvider beforeBodyWrite(@Nullable RespBodyProvider body, @Nonnull MethodParameter returnType, @Nonnull MediaType selectedContentType, @Nonnull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @Nonnull ServerHttpRequest request, @Nonnull ServerHttpResponse response) {
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