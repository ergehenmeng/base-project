package com.eghm.web;

import com.eghm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 前后端分离
 *
 * @author 二哥很猛
 * @date
 */
@SpringBootApplication(scanBasePackages = "com.eghm")
@EnableAsync
@EnableAspectJAutoProxy
@MapperScan("com.eghm.dao.mapper")
@Slf4j
public class ApiApplication implements ApplicationListener<ContextRefreshedEvent> {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ApiApplication.class).bannerMode(Banner.Mode.OFF).web(WebApplicationType.SERVLET).run(args);
        log.info("\n-------------------------------------------------\n\t" +
                "Swagger文档: http://localhost:{}/doc.html\n" +
                "-------------------------------------------------", context.getEnvironment().getProperty("server.port"));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        SpringContextUtil.setApplicationContext(event.getApplicationContext());
    }
}
