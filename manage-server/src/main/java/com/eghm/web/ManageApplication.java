package com.eghm.web;


import cn.hutool.core.net.NetUtil;
import com.eghm.annotation.EnableSchedulingTask;
import com.eghm.utils.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 程序启动入口
 * 排除RabbitConfig是因为管理后台暂不使用mq
 *
 * @author 二哥很猛
 */
@Slf4j
@EnableAsync
@EnableSchedulingTask
@ComponentScan("com.eghm")
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan(basePackages = "com.eghm.mapper")
@SpringBootApplication(exclude = FlywayAutoConfiguration.class)
public class ManageApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ManageApplication.class).bannerMode(Banner.Mode.OFF).run(args);
        LoggerUtil.print(String.format("Swagger文档: http://%s:%s/doc.html", NetUtil.getLocalhostStr(), context.getEnvironment().getProperty("server.port")));
    }

}
