package com.fanyin.configuration.handler;

import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.configuration.DatePropertyEditor;
import com.fanyin.dao.model.system.ExceptionLog;
import com.fanyin.model.ext.RequestThreadLocal;
import com.fanyin.model.ext.RespBody;
import com.fanyin.queue.TaskHandler;
import com.fanyin.queue.task.ExceptionLogTask;
import com.fanyin.service.system.ExceptionLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/6/21 12:09
 */
@ControllerAdvice
@Slf4j
public class ControllerAdviceHandler {

    @Autowired
    private ExceptionLogService exceptionLogService;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class,new DatePropertyEditor());
    }

    /**
     * 业务异常统一拦截
     * @param e 异常
     * @return 返回标准对象
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public RespBody businessException(HttpServletRequest request, BusinessException e){
        log.error("业务异常:[{}] [{}:{}]",request.getRequestURI(),e.getCode(),e.getMessage());
        return RespBody.getInstance().setCode(e.getCode()).setMsg(e.getMessage());
    }

    /**
     * 系统级异常统一拦截
     * @param e 异常
     * @return 返回标准对象
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RespBody exception(HttpServletRequest request, Exception e){
        log.error("系统异常 url:[{}]",request.getRequestURI(),e);
        ExceptionLog exceptionLog = ExceptionLog.builder().url(request.getRequestURI()).requestParam(RequestThreadLocal.getRequestBody()).errorMsg(ExceptionUtils.getStackTrace(e)).build();
        TaskHandler.executeOperateLog(new ExceptionLogTask(exceptionLog,exceptionLogService));
        return RespBody.error(ErrorCode.SYSTEM_ERROR);
    }

    /**
     * 非系统url请求
     * @param request 请求request
     * @return 404
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public RespBody noHandlerFoundException(HttpServletRequest request){
        log.error("访问地址不存在:[{}]",request.getRequestURI());
        return RespBody.error(ErrorCode.PAGE_NOT_FOUND);
    }

}
