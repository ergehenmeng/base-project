package com.eghm.configuration.handler;

import com.eghm.annotation.Mark;
import com.eghm.configuration.security.SecurityOperator;
import com.eghm.configuration.security.SecurityOperatorHolder;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.model.SysOperationLog;
import com.eghm.queue.TaskHandler;
import com.eghm.queue.task.OperationLogTask;
import com.eghm.service.common.JsonService;
import com.eghm.service.sys.OperationLogService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 操作日志
 *
 * @author 二哥很猛
 * @date 2019/1/15 16:19
 */
@Component
@Aspect
@Slf4j(topic = "request_response")
public class OperationLogAspect {

    private SysConfigApi sysConfigApi;

    private OperationLogService operationLogService;

    private JsonService jsonService;

    private TaskHandler taskHandler;

    @Autowired
    public void setSysConfigApi(SysConfigApi sysConfigApi) {
        this.sysConfigApi = sysConfigApi;
    }

    @Autowired
    public void setOperationLogService(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @Autowired
    public void setJsonService(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    @Autowired
    public void setTaskHandler(TaskHandler taskHandler) {
        this.taskHandler = taskHandler;
    }

    /**
     * 操作日志,如果仅仅想请求或者响应某些参数不想入库可以在响应字段上添加
     * {@link com.google.gson.annotations.Expose} serialize = false
     *
     * @param joinPoint 切入点
     * @param mark      操作日志标示注解
     * @return aop方法调用结果对象
     * @throws Throwable 异常
     */
    @Around("@annotation(mark) && within(com.eghm.controller..*)")
    public Object around(ProceedingJoinPoint joinPoint, Mark mark) throws Throwable {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return joinPoint.proceed();
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        SecurityOperator operator = SecurityOperatorHolder.getOperator();
        if (operator == null) {
            log.warn("操作日志无法查询到登陆用户 url:[{}]", request.getRequestURI());
            return joinPoint.proceed();
        }
        SysOperationLog sy = new SysOperationLog();

        sy.setOperatorId(operator.getId());
        sy.setOperatorName(operator.getOperatorName());
        sy.setIp(IpUtil.ipToLong(IpUtil.getIpAddress(request)));

        if (mark.request()) {
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                sy.setRequest(formatRequest(args));
            }
        }
        sy.setUrl(request.getRequestURI());
        Object proceed = joinPoint.proceed();
        long end = System.currentTimeMillis();
        sy.setBusinessTime(end - System.currentTimeMillis());
        if (mark.response() && proceed != null) {
            sy.setResponse(jsonService.toJson(proceed));
        }
        boolean logSwitch = sysConfigApi.getBoolean(ConfigConstant.OPERATION_LOG_SWITCH);
        if (logSwitch) {
            taskHandler.executeOperateLog(new OperationLogTask(sy, operationLogService));
        } else {
            log.info("请求地址:[{}],请求参数:[{}],响应参数:[{}],请求ip:[{}],操作id:[{}],耗时:[{}]", sy.getUrl(), sy.getRequest(), sy.getResponse(), sy.getIp(), sy.getOperatorId(), sy.getBusinessTime());
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
                builder.append(",");
            }
            // 过滤内置参数
            boolean flag = object instanceof HttpServletRequest || object instanceof HttpServletResponse || object instanceof MultipartFile || object instanceof Model;

            if (flag) {
                continue;
            }
            builder.append(jsonService.toJson(object));
        }
        return builder.toString();
    }
}
