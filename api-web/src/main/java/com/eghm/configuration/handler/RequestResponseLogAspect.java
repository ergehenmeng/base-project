package com.eghm.configuration.handler;


import com.eghm.model.ext.RequestMessage;
import com.eghm.model.ext.RequestThreadLocal;
import com.eghm.utils.IpUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * api请求响应日志记录
 * @author 二哥很猛
 */
@Slf4j(topic = "request_response")
@Aspect
@Component
public class RequestResponseLogAspect {

    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    /**
     * 操作日志,如果仅仅想请求或者响应某些参数不想入库可以在响应字段上添加
     * {@link com.google.gson.annotations.Expose} serialize = false
     *
     * @param joinPoint 切入点
     * @return aop方法调用结果对象
     * @throws Throwable 异常
     */
    @Around("(!@annotation(com.eghm.annotation.SkipLogger)) && within(com.eghm.controller..*)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return joinPoint.proceed();
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String ip = IpUtil.getIpAddress(request);
        String uri = request.getRequestURI();
        RequestMessage message = RequestThreadLocal.get();
        try {
            long start = System.currentTimeMillis();
            Object proceed = joinPoint.proceed();
            long end = System.currentTimeMillis();
            if (log.isDebugEnabled()) {
                log.debug("请求地址:[{}],请求ip:[{}],操作id:[{}],请求参数:[{}],响应参数:[{}],耗时:[{}ms],软件版本:[{}],客户端:[{}],系统版本:[{}],设备厂商:[{}],设备型号:[{}]",
                        uri, ip,message.getUserId(), message.getRequestBody(), this.jsonFormat(proceed) , end-start, message.getVersion(), message.getChannel(), message.getOsVersion(), message.getDeviceBrand(), message.getDeviceModel());
            }
            return proceed;
        } catch (Throwable e) {
            log.warn("请求地址:[{}],请求ip:[{}],操作id:[{}],请求参数:[{}],响应参数:[{}],耗时:[{}ms],软件版本:[{}],客户端:[{}],系统版本:[{}],设备厂商:[{}],设备型号:[{}]",
                    uri, ip,message.getUserId(), message.getRequestBody(), "接口异常" ,0 , message.getVersion(), message.getChannel(), message.getOsVersion(), message.getDeviceBrand(), message.getDeviceModel());
            throw e;
        }
    }

    /**
     * RequestMapping方法返回值格式化输出
     *
     * @param proceed 结果值
     * @return 格式化输出
     */
    private Object jsonFormat(Object proceed) {
        if (proceed == null || proceed instanceof Void || proceed instanceof String) {
            return proceed;
        }
        return gson.toJson(proceed);
    }
}
