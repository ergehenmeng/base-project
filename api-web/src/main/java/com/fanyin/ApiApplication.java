package com.fanyin;

import com.fanyin.utils.SpringContextUtil;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 前后端分离
 * @author 二哥很猛
 * @date
 */
@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy
public class ApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(ApiApplication.class).bannerMode(Banner.Mode.OFF).web(WebApplicationType.SERVLET).run(args);
        SpringContextUtil.setApplicationContext(applicationContext);
    }
}
