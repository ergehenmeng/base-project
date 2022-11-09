package com.eghm.web.configuration.handler;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.DatePropertyEditor;
import com.eghm.model.dto.ext.RespBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
        return RespBody.error(ErrorCode.SYSTEM_ERROR);
    }

    /**
     * 方法请求问题
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RespBody<Void> exception(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        log.error("系统异常, 接口[{}]不支持[{}]请求方式", request.getRequestURI(), e.getMethod());
        return RespBody.error(ErrorCode.METHOD_NOT_SUPPOERTED.getCode(), String.format(ErrorCode.METHOD_NOT_SUPPOERTED.getMsg(), e.getMethod()));
    }
}
