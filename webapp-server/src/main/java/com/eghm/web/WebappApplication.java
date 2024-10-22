package com.eghm.web;

import com.eghm.utils.LoggerUtil;
import com.eghm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 前后端分离
 * 默认不激活邮件功能,如需激活则将下面 exclude = MailSenderAutoConfiguration.class 去掉, 同时在配置文件中填写正确的邮箱配置
 *
 * @author 二哥很猛
 * @since 2019/8/9
 */
@Slf4j
@EnableAsync
@MapperScan("com.eghm.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication(scanBasePackages = "com.eghm", exclude = MailSenderAutoConfiguration.class)
public class WebappApplication implements ApplicationListener<ContextRefreshedEvent> {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(WebappApplication.class).bannerMode(Banner.Mode.OFF).web(WebApplicationType.SERVLET).run(args);
        LoggerUtil.print(String.format("Swagger文档: http://%s:%s/doc.html", InetAddress.getLocalHost().getHostAddress(), context.getEnvironment().getProperty("server.port")));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        SpringContextUtil.setApplicationContext(event.getApplicationContext());
    }

}
