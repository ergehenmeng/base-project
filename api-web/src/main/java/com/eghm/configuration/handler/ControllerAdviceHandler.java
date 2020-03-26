package com.eghm.configuration.handler;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.DatePropertyEditor;
import com.eghm.annotation.SkipEncrypt;
import com.eghm.dao.model.system.ExceptionLog;
import com.eghm.model.ext.RequestThreadLocal;
import com.eghm.model.ext.RespBody;
import com.eghm.queue.TaskHandler;
import com.eghm.queue.task.ExceptionLogTask;
import com.eghm.service.system.ExceptionLogService;
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

    @Autowired
    private TaskHandler taskHandler;

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
    @ResponseBody
    @SkipEncrypt
    public RespBody<Object> businessException(HttpServletRequest request, BusinessException e) {
        log.error("业务异常:[{}] [{}:{}]", request.getRequestURI(), e.getCode(), e.getMessage());
        return RespBody.error(e.getCode(), e.getMessage());
    }

    /**
     * 系统级异常统一拦截
     *
     * @param e 异常
     * @return 返回标准对象
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @SkipEncrypt
    public RespBody<Object> exception(HttpServletRequest request, Exception e) {
        log.error("系统异常 url:[{}]", request.getRequestURI(), e);
        ExceptionLog exceptionLog = ExceptionLog.builder().url(request.getRequestURI()).requestParam(RequestThreadLocal.getRequestBody()).errorMsg(ExceptionUtils.getStackTrace(e)).build();
        taskHandler.executeExceptionLog(new ExceptionLogTask(exceptionLog, exceptionLogService));
        return RespBody.error(ErrorCode.SYSTEM_ERROR);
    }

    /**
     * 非系统url请求
     *
     * @param request 请求request
     * @return 404
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @SkipEncrypt
    public RespBody<Object> noHandlerFoundException(HttpServletRequest request) {
        log.error("访问地址不存在:[{}]", request.getRequestURI());
        return RespBody.error(ErrorCode.PAGE_NOT_FOUND);
    }

}
