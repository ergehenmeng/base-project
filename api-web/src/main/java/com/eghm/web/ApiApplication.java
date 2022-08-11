package com.eghm.web;

import com.eghm.service.mq.RabbitMessageService;
import com.eghm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
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

    @Autowired
    private RabbitMessageService rabbitService;

    @GetMapping("/sendDelay")
    public String sendDelay(String msg) {
        log.info("开始发送延迟: [{}]", msg);
        rabbitService.sendDelay(msg, "exchange_order_delay", 5);
        return "success";
    }

    @GetMapping("/send")
    public String send(String msg) {
        log.info("开始发送: [{}]", msg);
        rabbitService.send(msg, "exchange_dead_letter");
        return "success";
    }

}
