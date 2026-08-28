package com.eghm.app.webapp.configuration.handler;


import cn.hutool.core.exceptions.ExceptionUtil;
import com.eghm.foundation.core.configuration.authentication.ApiHolder;
import com.eghm.foundation.core.dto.ext.RequestMessage;
import com.eghm.foundation.core.enums.ExchangeQueue;
import com.eghm.foundation.web.config.log.LogTraceHolder;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.integration.messaging.service.MessageService;
import com.eghm.platform.audit.entity.WebappLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * api请求响应日志记录
 *
 * @author 二哥很猛
 */
@Slf4j(topic = "request_response")
@Aspect
@Order(1)
@Component
@AllArgsConstructor
public class WebappLogAspect {

    private final MessageService messageService;

    /**
     * 操作日志,采用默认jackson进行序列化
     *
     * @param joinPoint 切入点
     * @return aop方法调用结果对象
     * @throws Throwable 异常
     */
    @Around("(!@annotation(com.eghm.foundation.core.annotation.SkipLogger)) && within(com.eghm.app.webapp.controller..*)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return joinPoint.proceed();
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String uri = request.getRequestURI();
        RequestMessage message = ApiHolder.get();
        long start = System.currentTimeMillis();
        try {
            Object proceed = joinPoint.proceed();
            this.logRecord(message, uri, System.currentTimeMillis() - start, null);
            return proceed;
        } catch (Throwable e) {
            this.logRecord(message, uri, System.currentTimeMillis() - start, e);
            throw e;
        } finally {
            log.info("请求地址:[{}], 会员ID:[{}], 请求参数:[{}], 耗时:[{}ms], 软件版本:[{}], 客户端:[{}], 系统版本:[{}], 设备厂商:[{}], 设备型号:[{}]",
                    uri, message.getMemberId(), message.getRequestParam(), System.currentTimeMillis() - start, message.getVersion(),
                    message.getChannel(), message.getOsVersion(), message.getDeviceBrand(), message.getDeviceModel());
        }
    }

    /**
     * 记录日志
     *
     * @param message 请求消息
     * @param uri 请求接口
     * @param elapsedTime 耗时
     */
    private void logRecord(RequestMessage message, String uri, long elapsedTime, Throwable throwable) {
        try {
            WebappLog webappLog = DataUtil.copy(message, WebappLog.class);
            webappLog.setElapsedTime(elapsedTime);
            webappLog.setIp(LogTraceHolder.getClientIp());
            webappLog.setUrl(uri);
            webappLog.setTraceId(LogTraceHolder.getTraceId());
            webappLog.setRequestParam(message.getRequestParam());
            if (throwable != null) {
                webappLog.setErrorMsg(ExceptionUtil.stacktraceToString(throwable));
            }
            messageService.send(ExchangeQueue.WEBAPP_LOG, webappLog);
        } catch (Exception e) {
            log.error("系统日志保存异常 [{}]", uri, e);
        }
    }

}