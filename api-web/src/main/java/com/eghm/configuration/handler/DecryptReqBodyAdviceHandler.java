package com.eghm.configuration.handler;

import com.eghm.annotation.SkipAccess;
import com.eghm.common.constant.CommonConstant;
import com.eghm.common.utils.AesUtil;
import com.eghm.annotation.SkipDecrypt;
import com.eghm.model.ext.RequestThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * 请求信息解密操作, 用户必须已经登陆,且没有跳过解密的注解方可进行解密操作
 *
 * @author 二哥很猛
 */
@RestControllerAdvice
@Slf4j
public class DecryptReqBodyAdviceHandler extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return !methodParameter.hasMethodAnnotation(SkipDecrypt.class) && !methodParameter.hasMethodAnnotation(SkipAccess.class);
    }

    @Override
    public @NonNull HttpInputMessage beforeBodyRead(@NonNull HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return new RequestHttpInputMessage(inputMessage,RequestThreadLocal.get().getSecret());
    }
}
@Slf4j
class RequestHttpInputMessage implements HttpInputMessage {

    private InputStream body;

    private HttpHeaders headers;

    public RequestHttpInputMessage(HttpInputMessage inputMessage, String secret) throws IOException {
        this.headers = inputMessage.getHeaders();
        String content = IOUtils.toString(inputMessage.getBody(), CommonConstant.CHARSET);
        String decrypt = AesUtil.decrypt(content, secret);
        log.debug("原始数据:[{}],解密数据:[{}]", content, decrypt);
        this.body = IOUtils.toInputStream(decrypt, CommonConstant.CHARSET);
    }

    @Override
    public @NonNull InputStream getBody(){
        return body;
    }

    @Override
    public @NonNull HttpHeaders getHeaders() {
        return headers;
    }
}

