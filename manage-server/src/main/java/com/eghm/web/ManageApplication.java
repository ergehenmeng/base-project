package com.eghm.web;


import com.eghm.configuration.annotation.EnableTask;
import com.eghm.configuration.rabbit.RabbitConfig;
import com.eghm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 程序启动入口 
 *
 * @author 二哥很猛
 */
@SpringBootApplication(scanBasePackages = "com.eghm", exclude = {RabbitAutoConfiguration.class, RabbitConfig.class})
@EnableAsync
@EnableAspectJAutoProxy
@ServletComponentScan(basePackages = "com.eghm.configuration.listener")
@MapperScan(basePackages = "com.eghm.mapper")
@EnableTask
@Slf4j
public class ManageApplication implements ApplicationContextAware {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ManageApplication.class).bannerMode(Banner.Mode.OFF).run(args);
        log.info("\n-------------------------------------------------\n\t" +
                "Swagger文档:  http://localhost:{}/doc.html\n" +
                "-------------------------------------------------", context.getEnvironment().getProperty("server.port"));
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.setApplicationContext(applicationContext);
    }

}
