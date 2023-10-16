package com.eghm.web.configuration.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.eghm.configuration.log.LogTraceHolder;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RequestMessage;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.exception.*;
import com.eghm.model.WebappLog;
import com.eghm.service.mq.service.MessageService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.IpUtil;
import com.eghm.utils.WebUtil;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2019/6/21 12:09
 */
@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class ExceptionAdviceHandler {

    private final MessageService rabbitMessageService;

    /**
     * 特殊业务异常统一拦截
     *
     * @param e 异常
     * @return 返回标准对象
     */
    @ExceptionHandler(DataException.class)
    public RespBody<Object> dataException(HttpServletRequest request, DataException e) {
        log.warn("特殊业务异常:[{}] 错误码:[{}]", request.getRequestURI(), e.getCode(), e);
        RespBody<Object> body = RespBody.error(e.getCode(), e.getMessage());
        body.setData(e.getData());
        return body;
    }

    /**
     * 业务异常统一拦截
     *
     * @param e 异常
     * @return 返回标准对象
     */
    @ExceptionHandler(BusinessException.class)
    public RespBody<Void> businessException(HttpServletRequest request, BusinessException e) {
        log.warn("业务异常:[{}] 错误码:[{}] ", request.getRequestURI(), e.getCode(), e);
        return RespBody.error(e.getCode(), e.getMessage());
    }
    
    /**
     * 参数异常
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
     * 系统级异常统一拦截
     *
     * @param e 异常
     * @return 返回标准对象
     */
    @ExceptionHandler(Exception.class)
    public RespBody<Void> exception(HttpServletRequest request, Exception e) {
        log.error("系统异常 [{}]", request.getRequestURI(), e);
        RequestMessage message = ApiHolder.get();
        WebappLog webappLog = DataUtil.copy(message, WebappLog.class);
        webappLog.setUrl(request.getRequestURI());
        webappLog.setIp(IpUtil.getIpAddress(request));
        webappLog.setMemberId(ApiHolder.tryGetMemberId());
        webappLog.setRequestParam(ApiHolder.getRequestParam());
        webappLog.setTraceId(LogTraceHolder.getTraceId());
        webappLog.setErrorMsg(ExceptionUtil.stacktraceToString(e));
        rabbitMessageService.send(ExchangeQueue.WEBAPP_LOG, webappLog);
        return RespBody.error(ErrorCode.SYSTEM_ERROR);
    }

    /**
     * 请求类型不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RespBody<Void> exception(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        log.error("系统异常, 接口[{}]不支持[{}]请求方式", request.getRequestURI(), e.getMethod());
        return RespBody.error(ErrorCode.METHOD_NOT_SUPPORTED.getCode(), String.format(ErrorCode.METHOD_NOT_SUPPORTED.getMsg(), e.getMethod()));
    }

    /**
     * 非系统url请求
     *
     * @param request 请求request
     * @return 404
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public RespBody<Void> noHandlerFoundException(HttpServletRequest request) {
        log.warn("访问地址不存在:[{}]", request.getRequestURI());
        return RespBody.error(ErrorCode.PAGE_NOT_FOUND);
    }

    /**
     * 参数绑定失败
     */
    @ExceptionHandler(BindException.class)
    public RespBody<Void> exception(HttpServletRequest request, BindException e) {
        log.error("数据绑定异常, 接口[{}]", request.getRequestURI(), e);
        return WebUtil.fieldBind(e.getBindingResult());
    }

    /**
     * 参数校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespBody<Void> exception(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.error("参数校验异常, 接口[{}]", request.getRequestURI());
        return WebUtil.fieldValid(e.getBindingResult());
    }

    /**
     * 微信异步通知异常
     *
     * @return 404
     */
    @ExceptionHandler(WeChatPayException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> weChatPayException(HttpServletRequest request, WeChatPayException e) {
        log.error("微信异步通知异常 [{}]", request.getRequestURI());
        Map<String, String> map = Maps.newHashMapWithExpectedSize(2);
        map.put("code", "FAIL");
        map.put("message", e.getMessage());
        return map;
    }

    /**
     * 支付宝异步通知
     */
    @ExceptionHandler(AliPayException.class)
    public String aliPayException(HttpServletRequest request) {
        log.error("支付宝异步通知异常 [{}]", request.getRequestURI());
        return "FAIL";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RespBody<Void> notNullException(HttpServletRequest request, MissingServletRequestParameterException e) {
        log.error("参数校验为空, 接口[{}]", request.getRequestURI());
        return RespBody.error(ErrorCode.PARAM_NULL_ERROR.getCode(), String.format(ErrorCode.PARAM_NULL_ERROR.getMsg(), e.getParameterName()));
    }
}
