package com.fanyin.configuration.handler;

import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.configuration.DatePropertyEditor;
import com.fanyin.model.ext.RespBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2018/11/29 15:03
 */
@ControllerAdvice
@Slf4j
public class ControllerAdviceHandler {

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
        log.error("业务异常:[{}]",request.getRequestURI(),e);
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
        return RespBody.error(ErrorCodeEnum.SYSTEM_ERROR);
    }
}
