package com.fanyin.interceptor;

import com.fanyin.annotation.SkipDataBinder;
import com.fanyin.common.constant.CommonConstant;
import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.ParameterException;
import com.fanyin.common.exception.RequestException;
import com.fanyin.model.ext.RequestThreadLocal;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Nullable;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 数据校验解析器
 * @author 二哥很猛
 * @date 2018/1/8 14:42
 */
public class JsonHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supportsParameter(@Nullable MethodParameter parameter) {
        if(parameter == null || parameter.hasMethodAnnotation(SkipDataBinder.class)){
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
        if(webRequest == null || parameter == null){
            throw new RequestException(ErrorCode.REQUEST_RESOLVE_ERROR);
        }

        HttpServletRequest request = ((ServletWebRequest)webRequest).getRequest();

        Object args = jsonFormat(request, parameter.getParameterType());
        WebDataBinder binder = binderFactory.createBinder(webRequest, args, parameter.getParameterType().getName());
        binder.validate(args);
        BindingResult bindingResult = binder.getBindingResult();
        if(bindingResult.hasErrors()){
            //只显示第一条校验失败的信息
            ObjectError objectError = bindingResult.getAllErrors().get(0);
            throw new RequestException(ErrorCode.PARAM_VERIFY_ERROR.getCode(),objectError.getDefaultMessage());
        }
        return args;
    }


    /**
     * 将request中对象转换为转换为指定接收的参数对象
     * @param request 请求信息
     * @param cls 接收参数的类型
     * @return 结果对象
     */
    private Object jsonFormat(HttpServletRequest request,Class<?> cls){
        try {
            //如果开启签名,则此处可以获取到前台的json
            String requestBody = RequestThreadLocal.getRequestBody();
            if(requestBody != null){
                return objectMapper.readValue(requestBody,cls);
            }
            String args = IOUtils.toString(request.getInputStream(), CommonConstant.CHARSET);
            if(args == null){
                return cls.newInstance();
            }
            return objectMapper.readValue(args,cls);
        }catch (Exception e){
            throw new ParameterException(ErrorCode.JSON_FORMAT_ERROR);
        }
    }
}
