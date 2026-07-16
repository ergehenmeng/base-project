package com.eghm.app.manage;


import cn.hutool.core.net.NetUtil;
import com.eghm.platform.job.annotation.EnableSchedulingTask;
import com.eghm.foundation.web.utility.LoggerUtil;
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
 * 排除FlywayAutoConfiguration是因为Spring Boot高版本使用高版本flyway时, flyway不支持mysql5.7及以下版本
 * 因此必须使用低版本flyway, 但是低版本flyway不兼容高版本Spring Boot自动化配置, 因此需要手动配置flywayBean
 *
 * @author 二哥很猛
 */
@Slf4j
@EnableAsync
@EnableSchedulingTask
@ComponentScan(basePackages = "com.eghm")
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan(basePackages = "com.eghm.**.mapper")
@SpringBootApplication(exclude = FlywayAutoConfiguration.class)
public class ManageApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ManageApplication.class).bannerMode(Banner.Mode.OFF).run(args);
        LoggerUtil.print(String.format("Swagger文档: http://%s:%s/doc.html", NetUtil.getLocalhostStr(), context.getEnvironment().getProperty("server.port")));
    }

}
