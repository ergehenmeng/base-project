package com.eghm.permission;

import com.eghm.configuration.security.SecurityOperator;
import com.eghm.controller.AbstractController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author 殿小二
 * @date 2020/8/14
 */
@Aspect
@Component
public class DataScopeAspect {

    private static final ThreadLocal<String> DATA_SCOPE_PARAM = new ThreadLocal<>();

    /**
     * 切点
     */
    @Pointcut("@annotation(com.eghm.permission.DataScope) && within(com.eghm.dao.mapper..*)")
    public void pointcut() {
    }

    /**
     * 增强逻辑
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        try {
            SecurityOperator operator = AbstractController.getRequiredOperator();
            return joinPoint.proceed();
        } finally {
            DATA_SCOPE_PARAM.remove();
        }
    }
}
