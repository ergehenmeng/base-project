package com.eghm.app.manage.configuration.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.eghm.foundation.core.configuration.authentication.SecurityHolder;
import com.eghm.foundation.core.enums.ExchangeQueue;
import com.eghm.foundation.core.security.UserToken;
import com.eghm.foundation.core.service.JsonService;
import com.eghm.foundation.web.utility.IpUtil;
import com.eghm.foundation.web.utility.WebUtil;
import com.eghm.integration.messaging.service.MessageService;
import com.eghm.platform.audit.entity.ManageLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 操作日志
 *
 * @author 二哥很猛
 * @since 2019/1/15 16:19
 */
@Aspect
@Component
@AllArgsConstructor
@Slf4j(topic = "request_response")
public class ManageLogAspect {

    private final JsonService jsonService;

    private final MessageService messageService;

    /**
     * 操作日志,如果请求参数不想入库可以在响应字段上添加 @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
     *
     * @param joinPoint 切入点
     * @return aop方法调用结果对象
     * @throws Throwable 异常
     */
    @Around("(!@annotation(com.eghm.foundation.core.annotation.SkipLogger)) && within(com.eghm.app.manage.controller..*)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return joinPoint.proceed();
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        UserToken user = SecurityHolder.getUser();
        ManageLog sy = new ManageLog();
        sy.setUserId(user == null ? null : user.getId());
        sy.setIp(IpUtil.getIpAddress(request));
        sy.setUrl(request.getRequestURI());
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            sy.setRequest(this.formatRequest(args));
        }
        long start = System.currentTimeMillis();
        try {
            Object proceed = joinPoint.proceed();
            sy.setBusinessTime(System.currentTimeMillis() - start);
            if (HttpMethod.GET.name().equals(request.getMethod())) {
                log.info("请求地址:[{}], 请求参数:[{}], 请求ip:[{}], 用户id:[{}], 耗时:[{}]ms", sy.getUrl(), sy.getRequest(), sy.getIp(), sy.getUserId(), sy.getBusinessTime());
            } else {
                this.logRecord(proceed, sy, request.getRequestURI());
            }
            return proceed;
        } catch (Throwable e) {
            sy.setBusinessTime(System.currentTimeMillis() - start);
            sy.setResponse(ExceptionUtil.stacktraceToString(e));
            this.logRecord(null, sy, request.getRequestURI());
            throw e;
        }
    }
    
    /**
     * 记录成功日志
     *
     * @param proceed 返回值
     * @param sy 日志信息
     * @param uri 请求接口
     */
    private void logRecord(Object proceed, ManageLog sy, String uri) {
        try {
            if (proceed != null) {
                sy.setResponse(jsonService.toJson(proceed));
            }
            messageService.send(ExchangeQueue.MANAGE_LOG, sy);
        } catch (Exception e) {
            log.error("系统日志保存异常 [{}]", uri, e);
        }
    }

    /**
     * 格式化请求参数 逗号分割
     *
     * @param args 请求参数
     * @return requestParam
     */
    private String formatRequest(Object[] args) {
        StringBuilder builder = new StringBuilder();
        for (Object object : args) {
            if (!builder.isEmpty()) {
                builder.append("|");
            }
            // 过滤内置参数
            if (WebUtil.isAutoInject(object.getClass())) {
                continue;
            }
            builder.append(jsonService.toJson(object));
        }
        return builder.toString();
    }
}