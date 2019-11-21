package com.fanyin.interceptor;

import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.SystemException;
import com.fanyin.model.ext.RespBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局统一异常处理器,返回前台的数据格式由ReturnJson包装
 * @author 二哥很猛
 * @date 2018/1/18 16:20
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RespBody exception(Exception e, HttpServletRequest request){
        log.error("系统异常,url:[{}]",request.getRequestURI(),e);
        return RespBody.error(ErrorCode.SYSTEM_ERROR);
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(SystemException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RespBody systemException(SystemException e, HttpServletRequest request){
        log.error("业务异常,url:[{}]",request.getRequestURI());
        return RespBody.<String>getInstance().setCode(e.getCode()).setMsg(e.getMessage());
    }

    /**
     * 404异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RespBody exception(HttpServletRequest request){
        log.error("访问地址异常,url:[{}]",request.getRequestURI());
        return RespBody.error(ErrorCode.PAGE_NOT_FOUND);
    }
}
