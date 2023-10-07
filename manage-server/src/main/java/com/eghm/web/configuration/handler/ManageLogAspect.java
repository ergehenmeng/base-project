package com.eghm.web.configuration.handler;

import com.eghm.enums.ExchangeQueue;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.ConfigConstant;
import com.eghm.model.ManageLog;
import com.eghm.dto.ext.UserToken;
import com.eghm.service.mq.service.MessageService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.IpUtil;
import com.eghm.utils.WebUtil;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作日志
 *
 * @author 二哥很猛
 * @date 2019/1/15 16:19
 */
@Component
@Aspect
@Slf4j(topic = "request_response")
@AllArgsConstructor
public class ManageLogAspect {

    private final SysConfigApi sysConfigApi;

    private final Gson gson = new Gson();

    private final MessageService rabbitMessageService;

    /**
     * 操作日志,如果仅仅想请求或者响应某些参数不想入库可以在响应字段上添加
     * {@link com.google.gson.annotations.Expose} serialize = false
     * 此处用gson进行序列化的原因是因为post请求采用 RequestBody, Spring内部采用jackson解析,
     * 如果要要忽略某几个字段的话使用jackson的注解, 字段映射为空,业务上无法进行处理
     *
     * @param joinPoint 切入点
     * @return aop方法调用结果对象
     * @throws Throwable 异常
     */
    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping) && (!@annotation(com.eghm.configuration.annotation.SkipLogger)) && within(com.eghm.web.controller..*)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return joinPoint.proceed();
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        UserToken user = SecurityHolder.getUser();
        if (user == null) {
            log.warn("操作日志无法查询到登陆用户 url:[{}]", request.getRequestURI());
            return joinPoint.proceed();
        }
        ManageLog sy = new ManageLog();
        sy.setUserId(user.getId());
        sy.setIp(IpUtil.getIpAddress(request));
        sy.setUrl(request.getRequestURI());
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            sy.setRequest(this.formatRequest(args));
        }
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        sy.setBusinessTime(System.currentTimeMillis() - start);
        if (proceed != null) {
            sy.setResponse(gson.toJson(proceed));
        }
        boolean logSwitch = sysConfigApi.getBoolean(ConfigConstant.OPERATION_LOG_SWITCH);
        if (logSwitch) {
            rabbitMessageService.send(ExchangeQueue.MANAGE_OP_LOG, sy);
        } else {
            log.info("请求地址:[{}], 请求参数:[{}], 响应参数:[{}], 请求ip:[{}], 用户id:[{}], 耗时:[{}]", sy.getUrl(), sy.getRequest(), sy.getResponse(), sy.getIp(), sy.getUserId(), sy.getBusinessTime());
        }
        return proceed;
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
            if (builder.length() > 0) {
                builder.append("|");
            }
            // 过滤内置参数
            if (WebUtil.isAutoInject(object.getClass())) {
                continue;
            }
            builder.append(gson.toJson(object));
        }
        return builder.toString();
    }
}
