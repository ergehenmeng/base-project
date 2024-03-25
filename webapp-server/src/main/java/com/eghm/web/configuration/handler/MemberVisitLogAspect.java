package com.eghm.web.configuration.handler;


import com.eghm.constant.AppHeader;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.enums.ExchangeQueue;
import com.eghm.model.MemberVisitLog;
import com.eghm.mq.service.MessageService;
import com.eghm.web.annotation.VisitRecord;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 会员浏览记录 用于统计分析
 *
 * @author 二哥很猛
 */
@Slf4j
@Aspect
@Order(2)
@Component
@AllArgsConstructor
public class MemberVisitLogAspect {

    private final MessageService messageService;

    /**
     * 操作日志,采用默认jackson进行序列化
     *
     * @param joinPoint 切入点
     * @return aop方法调用结果对象
     * @throws Throwable 异常
     */
    @Around("@annotation(visitRecord) && within(com.eghm.web.controller..*)")
    public Object around(ProceedingJoinPoint joinPoint, VisitRecord visitRecord) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return joinPoint.proceed();
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        MemberVisitLog visitLog = new MemberVisitLog();
        visitLog.setMemberId(ApiHolder.tryGetMemberId());
        visitLog.setChannel(ApiHolder.getChannel());
        visitLog.setOpenId(request.getHeader(AppHeader.OPEN_ID));
        visitLog.setCreateDate(LocalDate.now());
        visitLog.setCreateTime(LocalDateTime.now());
        visitLog.setVisitType(visitRecord.value());
        messageService.send(ExchangeQueue.MEMBER_VISIT_LOG, visitLog);
        return joinPoint.proceed();
    }

}
