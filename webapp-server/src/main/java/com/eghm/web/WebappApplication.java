package com.eghm.web;

import com.eghm.configuration.rabbit.RabbitConfig;
import com.eghm.configuration.rabbit.RabbitInitConfig;
import com.eghm.utils.LoggerUtil;
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
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 前后端分离
 *
 * @author 二哥很猛
 * @date
 */
@Slf4j
@EnableAsync
@EnableAspectJAutoProxy
@Import({RabbitConfig.class, RabbitInitConfig.class})
@MapperScan("com.eghm.mapper")
@SpringBootApplication(scanBasePackages = "com.eghm")
public class WebappApplication implements ApplicationListener<ContextRefreshedEvent> {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(WebappApplication.class).bannerMode(Banner.Mode.OFF).web(WebApplicationType.SERVLET).run(args);
        LoggerUtil.print(String.format("Swagger文档: http://localhost:%s/doc.html", context.getEnvironment().getProperty("server.port")));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        SpringContextUtil.setApplicationContext(event.getApplicationContext());
    }

}
