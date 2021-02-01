package com.eghm.web.configuration.resolver;

import com.eghm.common.constant.CommonConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.ParameterException;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.PagingQuery;
import com.eghm.model.dto.ext.RequestMessage;
import com.eghm.utils.DataUtil;
import com.eghm.web.annotation.SkipDataBinder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * 数据绑定及校验器 只针对post请求有效
 *
 * @author 二哥很猛
 * @date 2018/1/8 14:42
 */
@Slf4j
public class JsonExtractHandlerArgumentResolver implements HandlerMethodArgumentResolver {

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(@NotNull MethodParameter parameter) {
        // 只针对 post请求,且没有标示@SkipDataBinder,并且不是内置对象才会进行Json数据绑定
        if (!parameter.hasMethodAnnotation(SkipDataBinder.class) && this.isPostRequest(parameter)) {
            Class<?> paramType = parameter.getParameterType();
            return !ServletRequest.class.isAssignableFrom(paramType) &&
                    !ServletResponse.class.isAssignableFrom(paramType) &&
                    !MultipartRequest.class.isAssignableFrom(paramType) &&
                    !MultipartFile.class.isAssignableFrom(paramType) &&
                    !HttpSession.class.isAssignableFrom(paramType);
        }
        return false;
    }

    @Override
    public Object resolveArgument(@Nullable MethodParameter parameter, ModelAndViewContainer mavContainer, @Nullable NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (webRequest == null || parameter == null) {
            throw new ParameterException(ErrorCode.REQUEST_RESOLVE_ERROR);
        }
        //注入RequestMessage对象
        if (parameter.getParameterType().isAssignableFrom(RequestMessage.class)) {
            return DataUtil.copy(ApiHolder.get(), RequestMessage.class);
        }
        Object args = this.jsonFormat(parameter);
        this.annotationValidate(binderFactory, webRequest, args, parameter.getParameterType().getName());
        this.validateMaxPageSize(args);
        return args;
    }

    /**
     * 参数对象的注解校验
     * @param binderFactory binderFactory
     * @param webRequest webRequest
     * @param args 对象
     * @param objName 对象名称
     * @throws Exception Exception
     */
    private void annotationValidate(WebDataBinderFactory binderFactory, NativeWebRequest webRequest, Object args, String objName) throws Exception {
        WebDataBinder binder = binderFactory.createBinder(webRequest, args, objName);
        binder.validate(args);
        BindingResult bindingResult = binder.getBindingResult();
        if (bindingResult.hasErrors()) {
            //只显示第一条校验失败的信息
            ObjectError objectError = bindingResult.getAllErrors().get(0);
            throw new ParameterException(ErrorCode.PARAM_VERIFY_ERROR.getCode(), objectError.getDefaultMessage());
        }
    }

    /**
     * 分页最大值校验
     * @param args 参数对象
     */
    private void validateMaxPageSize(Object args) {
        if (args instanceof PagingQuery ) {
            PagingQuery query = (PagingQuery) args;
            if (query.getPageSize() > CommonConstant.MAX_PAGE_SIZE) {
                log.warn("页容量[{}]过大, 自动采用系统默认最大页容量[{}]", query.getPageSize(), CommonConstant.MAX_PAGE_SIZE);
                query.setPageSize(CommonConstant.MAX_PAGE_SIZE);
            }
        }
    }

    /**
     * 将request中对象转换为转换为指定接收的参数对象
     *
     * @param parameter method参数信息
     * @return 结果对象
     */
    private Object jsonFormat(MethodParameter parameter) {
        try {
            String requestBody = ApiHolder.get().getRequestBody();
            Class<?> parameterType = parameter.getParameterType();
            if (requestBody == null) {
                return parameterType.getDeclaredConstructor().newInstance();
            }
            // 将解密后的数据继续放入到,以便于做日志记录
            ApiHolder.get().setRequestBody(requestBody);
            return objectMapper.readValue(requestBody, parameterType);
        } catch (Exception e) {
            log.error("请求的Json参数解析异常", e);
            throw new ParameterException(ErrorCode.JSON_FORMAT_ERROR);
        }
    }

    /**
     * 判断请求的接口是否为post请求
     * @param parameter 请求方法参数
     * @return true:是post请求 false:不是
     */
    private boolean isPostRequest(MethodParameter parameter) {
        if (parameter.hasMethodAnnotation(PostMapping.class)) {
            return true;
        }
        RequestMapping annotation = parameter.getMethodAnnotation(RequestMapping.class);
        return annotation != null && Arrays.stream(annotation.method()).anyMatch(method -> method == RequestMethod.POST);
    }
}
