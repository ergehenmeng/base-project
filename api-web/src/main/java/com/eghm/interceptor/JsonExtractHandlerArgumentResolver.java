package com.eghm.interceptor;

import com.eghm.annotation.SkipAccess;
import com.eghm.annotation.SkipDataBinder;
import com.eghm.annotation.SkipDecrypt;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.ParameterException;
import com.eghm.common.exception.RequestException;
import com.eghm.common.utils.AesUtil;
import com.eghm.model.ext.RequestMessage;
import com.eghm.model.ext.RequestThreadLocal;
import com.eghm.utils.DataUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Nullable;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 数据校验解析器
 *
 * @author 二哥很猛
 * @date 2018/1/8 14:42
 */
@Slf4j
public class JsonExtractHandlerArgumentResolver implements HandlerMethodArgumentResolver {

    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Override
    public boolean supportsParameter(@Nullable MethodParameter parameter) {
        if (parameter == null || parameter.hasMethodAnnotation(SkipDataBinder.class)) {
            return false;
        }
        Class<?> paramType = parameter.getParameterType();
        return !ServletRequest.class.isAssignableFrom(paramType) &&
                !ServletResponse.class.isAssignableFrom(paramType) &&
                !MultipartRequest.class.isAssignableFrom(paramType) &&
                !MultipartFile.class.isAssignableFrom(paramType) &&
                !HttpSession.class.isAssignableFrom(paramType);
    }

    @Override
    public Object resolveArgument(@Nullable MethodParameter parameter, ModelAndViewContainer mavContainer, @Nullable NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (webRequest == null || parameter == null) {
            throw new RequestException(ErrorCode.REQUEST_RESOLVE_ERROR);
        }
        //注入RequestMessage对象
        if (parameter.getParameterType().isAssignableFrom(RequestMessage.class)) {
            return DataUtil.copy(RequestThreadLocal.get(), RequestMessage.class);
        }
        Object args = jsonFormat(parameter);
        WebDataBinder binder = binderFactory.createBinder(webRequest, args, parameter.getParameterType().getName());
        binder.validate(args);
        BindingResult bindingResult = binder.getBindingResult();
        if (bindingResult.hasErrors()) {
            //只显示第一条校验失败的信息
            ObjectError objectError = bindingResult.getAllErrors().get(0);
            throw new RequestException(ErrorCode.PARAM_VERIFY_ERROR.getCode(), objectError.getDefaultMessage());
        }
        return args;
    }


    /**
     * 将request中对象转换为转换为指定接收的参数对象
     *
     * @param parameter method参数信息
     * @return 结果对象
     */
    private Object jsonFormat(MethodParameter parameter) {
        try {
            String requestBody = RequestThreadLocal.get().getRequestBody();
            Class<?> parameterType = parameter.getParameterType();
            if (requestBody == null) {
                return parameterType.getDeclaredConstructor().newInstance();
            }
            // 解密
            requestBody = this.decryptRequestBody(requestBody, parameter);
            // 将解密后的数据继续放入到,以便于做日志记录
            RequestThreadLocal.get().setRequestBody(requestBody);
            return gson.fromJson(requestBody, parameterType);
        } catch (Exception e) {
            throw new ParameterException(ErrorCode.JSON_FORMAT_ERROR);
        }
    }

    private String decryptRequestBody(String requestBody, MethodParameter parameter) {
        if (this.needDecrypt(parameter)) {
            String secret = RequestThreadLocal.get().getSecret();
            String decrypt = AesUtil.decrypt(requestBody, secret);
            if (log.isDebugEnabled()) {
                log.debug("原始数据:[{}],解密数据:[{}]", requestBody, decrypt);
            }
            return decrypt;
        }
        return requestBody;
    }

    /**
     * 是否需要解密步骤
     *
     * @param parameter controller方法参数信息
     * @return true:需要解密操作,false不需要
     */
    private boolean needDecrypt(MethodParameter parameter) {
        return !parameter.hasMethodAnnotation(SkipDecrypt.class) && !parameter.hasMethodAnnotation(SkipAccess.class);
    }
}
