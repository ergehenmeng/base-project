package com.eghm.utils;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.SystemException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

/**
 * spring上下文工具类
 *
 * @author 二哥很猛
 * @since 2018/1/18 18:44
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpringContextUtil {

    @Getter
    private static ApplicationContext applicationContext;

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

    private static void verifyApplication() {
        if (applicationContext == null) {
            throw new SystemException(ErrorCode.SPRING_ON_LOADING);
        }
    }

}
