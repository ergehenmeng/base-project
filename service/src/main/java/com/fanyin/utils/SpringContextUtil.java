package com.fanyin.utils;

import org.springframework.context.ApplicationContext;

/**
 * spring上下文工具类
 * @author 二哥很猛
 * @date 2018/1/18 18:44
 */
public class SpringContextUtil {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext){
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

    public static Object getBean(Class<?> requiredType){
        return applicationContext.getBean(requiredType);
    }
}
