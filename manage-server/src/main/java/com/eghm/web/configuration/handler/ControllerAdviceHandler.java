package com.eghm.web.configuration.handler;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.exception.ParameterException;
import com.eghm.configuration.DatePropertyEditor;
import com.eghm.dto.ext.RespBody;
import com.eghm.utils.WebUtil;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2018/11/29 15:03
 */
@RestControllerAdvice
@Slf4j
public class ControllerAdviceHandler {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DatePropertyEditor());
    }

    /**
     * 业务异常统一拦截
     *
     * @param e 异常
     * @return 返回标准对象
     */
    @ExceptionHandler(BusinessException.class)
    public RespBody<Void> businessException(HttpServletRequest request, BusinessException e) {
        log.warn("业务异常:[{}] [{}:{}]", request.getRequestURI(), e.getCode(), e.getMessage());
        return RespBody.error(e.getCode(), e.getMessage());
    }

    /**
     * 系统级异常统一拦截
     *
     * @param e 异常
     * @return 返回标准对象
     */
    @ExceptionHandler(Exception.class)
    public RespBody<Void> exception(HttpServletRequest request, Exception e) {
        log.error("系统异常 [{}]", request.getRequestURI(), e);
        // json绑定异常
        if (e instanceof HttpMessageNotReadableException && e.getCause() instanceof ValueInstantiationException && e.getCause().getCause() instanceof BusinessException) {
            BusinessException exception = (BusinessException) e.getCause().getCause();
            return RespBody.error(exception.getCode(), exception.getMessage());
        }
        return RespBody.error(ErrorCode.SYSTEM_ERROR);
    }

    /**
     * 方法请求问题
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RespBody<Void> exception(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        log.error("系统异常, 接口[{}]不支持[{}]请求方式", request.getRequestURI(), e.getMethod());
        return RespBody.error(ErrorCode.METHOD_NOT_SUPPORTED.getCode(), String.format(ErrorCode.METHOD_NOT_SUPPORTED.getMsg(), e.getMethod()));
    }

    /**
     * 业务异常统一拦截
     *
     * @param e 异常
     * @return 返回标准对象
     */
    @ExceptionHandler(ParameterException.class)
    public RespBody<Void> parameterException(HttpServletRequest request, ParameterException e) {
        log.warn("参数异常:[{}] [{}:{}]", request.getRequestURI(), e.getCode(), e.getMessage());
        return RespBody.error(e.getCode(), e.getMessage());
    }

    /**
     * 参数校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespBody<Void> exception(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.error("参数校验异常, 接口[{}]", request.getRequestURI());
        return WebUtil.fieldError(e.getBindingResult());
    }

    /**
     * 参数校验失败
     */
    @ExceptionHandler(BindException.class)
    public RespBody<Void> exception(HttpServletRequest request, BindException e) {
        log.error("数据绑定异常, 接口[{}]", request.getRequestURI());
        return WebUtil.fieldError(e.getBindingResult());
    }

}
