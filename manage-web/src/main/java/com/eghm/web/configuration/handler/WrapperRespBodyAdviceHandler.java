package com.eghm.web.configuration.handler;


import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.annotation.SkipWrapper;
import com.eghm.model.dto.ext.RespBody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @date 2019/7/30 18:44
 */
@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class WrapperRespBodyAdviceHandler implements ResponseBodyAdvice<Object> {
    
    private SystemProperties systemProperties;

    @Override
    public boolean supports(@NotNull MethodParameter returnType, @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        // 只针对部分controller且没有标示SkipWrapper的返回值进行包装
        return systemProperties.getBasePackage() != null
                && returnType.getDeclaringClass().getPackage().getName().startsWith(systemProperties.getBasePackage())
                && !returnType.hasMethodAnnotation(SkipWrapper.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, @Nullable MethodParameter returnType, @Nullable MediaType selectedContentType, @Nullable Class<? extends HttpMessageConverter<?>> selectedConverterType, @Nullable ServerHttpRequest request, @Nullable ServerHttpResponse response) {
        if (body instanceof RespBody) {
            return body;
        }
        return RespBody.success(body);
    }
}
