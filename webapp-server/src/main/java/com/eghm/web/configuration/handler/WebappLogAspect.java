package com.eghm.web.configuration.handler;


import com.eghm.configuration.log.LogTraceHolder;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RequestMessage;
import com.eghm.enums.ExchangeQueue;
import com.eghm.model.WebappLog;
import com.eghm.service.mq.service.MessageService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.IpUtil;
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

import javax.servlet.http.HttpServletRequest;

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
    @Around("(!@annotation(com.eghm.configuration.annotation.SkipLogger)) && within(com.eghm.web.controller..*)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return joinPoint.proceed();
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String ip = IpUtil.getIpAddress(request);
        String uri = request.getRequestURI();
        RequestMessage message = ApiHolder.get();
        long elapsedTime = 0L;
        try {
            long start = System.currentTimeMillis();
            Object proceed = joinPoint.proceed();
            elapsedTime = System.currentTimeMillis() - start;
            WebappLog webappLog = DataUtil.copy(message, WebappLog.class);
            webappLog.setElapsedTime(elapsedTime);
            webappLog.setIp(ip);
            webappLog.setUrl(uri);
            webappLog.setTraceId(LogTraceHolder.getTraceId());
            webappLog.setRequestParam(message.getRequestParam());
            messageService.send(ExchangeQueue.WEBAPP_LOG, webappLog);
            return proceed;
        } finally {
            log.info("请求地址:[{}], 请求ip:[{}], 会员ID:[{}], 请求参数:[{}], 耗时:[{}ms], 软件版本:[{}], 客户端:[{}], 系统版本:[{}], 设备厂商:[{}], 设备型号:[{}]",
                    uri, ip, message.getMemberId(), message.getRequestParam(), elapsedTime, message.getVersion(),
                    message.getChannel(), message.getOsVersion(), message.getDeviceBrand(), message.getDeviceModel());
        }
    }

}
