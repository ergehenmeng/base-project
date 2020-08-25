package com.eghm.utils;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

/**
 * spring上下文工具类
 *
 * @author 二哥很猛
 * @date 2018/1/18 18:44
 */
@Slf4j
public class SpringContextUtil {

    private SpringContextUtil() {
    }


    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        verifyApplication();
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> cls) {
        verifyApplication();
        return applicationContext.getBean(beanName, cls);
    }

    public static <T> T getBean(Class<T> requiredType) {
        verifyApplication();
        return applicationContext.getBean(requiredType);
    }

    public static void verifyApplication() {
        if (applicationContext == null) {
            throw new SystemException(ErrorCode.SPRING_ON_LOADING);
        }
    }

}
