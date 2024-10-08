package com.eghm.web;


import com.eghm.annotation.EnableTask;
import com.eghm.utils.LoggerUtil;
import com.eghm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 程序启动入口
 * 排除RabbitConfig是因为管理后台暂不使用mq
 *
 * @author 二哥很猛
 */
@Slf4j
@EnableTask
@EnableAsync
@SpringBootApplication
@ComponentScan("com.eghm")
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan(basePackages = "com.eghm.mapper")
public class ManageApplication implements ApplicationContextAware {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ManageApplication.class).bannerMode(Banner.Mode.OFF).run(args);
        LoggerUtil.print(String.format("Swagger文档: http://localhost:%s/doc.html", context.getEnvironment().getProperty("server.port")));
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.setApplicationContext(applicationContext);
    }

}
