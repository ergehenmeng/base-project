package com.eghm.configuration.handler;


import com.eghm.annotation.SkipAccess;
import com.eghm.common.utils.AesUtil;
import com.eghm.configuration.annotation.SkipEncrypt;
import com.eghm.configuration.annotation.SkipWrapper;
import com.eghm.model.ext.RequestThreadLocal;
import com.eghm.model.ext.RespBody;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Nullable;

/**
 * @author 二哥很猛
 * @date 2019/7/30 18:44
 */
@RestControllerAdvice
@Slf4j
@SuppressWarnings("rawtypes")
public class EncryptRespBodyAdviceHandler implements ResponseBodyAdvice<Object> {

    @Autowired
    private Gson gson;

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.hasMethodAnnotation(SkipWrapper.class)
                && !returnType.hasMethodAnnotation(SkipEncrypt.class)
                && !returnType.hasMethodAnnotation(SkipAccess.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @Nullable ServerHttpRequest request, @Nullable ServerHttpResponse response) {
        //过滤掉swagger2返回前台的数据
        if (body instanceof Json) {
            return body;
        }
        //只对响应信息中的data进行加密,减少前端不必要的解密工作
        if (body instanceof RespBody) {
            RespBody respBody = (RespBody) body;
            String encrypt = null;
            if (respBody.getData() != null) {
                encrypt = AesUtil.encrypt(gson.toJson(respBody.getData()), RequestThreadLocal.get().getSecret());
            }
            return RespBody.success(encrypt);
        }
        return RespBody.success(AesUtil.encrypt(gson.toJson(body), RequestThreadLocal.get().getSecret()));
    }

}
