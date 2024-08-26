package com.eghm.web.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.SysMenuMapper;
import com.eghm.model.SysMenu;
import com.eghm.service.business.MybatisService;
import com.eghm.web.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * @author 二哥很猛
 * @since 2023/7/13
 */
@Slf4j
class MybatisTest extends BaseTest {

    @Autowired
    private MybatisService mybatisService;

    @Autowired
    private SysMenuMapper sysMenuMapper;


    @Test
    void test() {
        mybatisService.test();
    }

    @Test
    void initCode() {
        String[] forAnnotation = webApplicationContext.getBeanNamesForAnnotation(RestController.class);
        for (String beanName : forAnnotation) {
            Object bean = webApplicationContext.getBean(beanName);
            Class<?> cls;
            if (AopUtils.isAopProxy(bean)) {
                cls = webApplicationContext.getBean(beanName).getClass().getSuperclass();
            } else {
                cls = webApplicationContext.getBean(beanName).getClass();
            }
            RequestMapping annotation = cls.getDeclaredAnnotation(RequestMapping.class);
            if (annotation == null) {
                log.info("bean未查询到 {}", beanName);
                continue;
            }
            String value = annotation.value()[0];
            for (Method method : cls.getDeclaredMethods()) {
                GetMapping mapping = method.getDeclaredAnnotation(GetMapping.class);
                String path = null;
                if (mapping != null) {
                    path = mapping.value()[0];
                }
                if (path == null) {
                    PostMapping postMapping = method.getDeclaredAnnotation(PostMapping.class);
                    if (postMapping != null) {
                        path = postMapping.value()[0];
                    }
                }

                if (path != null) {
                    LambdaQueryWrapper<SysMenu> wrapper = Wrappers.lambdaQuery();
                    wrapper.like(SysMenu::getSubPath, value + path);
                    Long count = sysMenuMapper.selectCount(wrapper);
                    if (count < 0) {
                        log.info("接口未定义: {}", value);
                    }
                } else {
                    log.info("方法未含注解 [{}] [{}]", bean, method.getName());
                }
            }
        }
    }
}
