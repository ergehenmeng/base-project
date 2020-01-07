package com.eghm;

import com.eghm.utils.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 前后端分离
 *
 * @author 二哥很猛
 * @date
 */
@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy
@MapperScan("com.eghm.dao.mapper")
public class ApiApplication implements ApplicationListener<ApplicationContextEvent> {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApiApplication.class).bannerMode(Banner.Mode.OFF).web(WebApplicationType.SERVLET).run(args);
    }

    @Override
    public void onApplicationEvent(@NonNull ApplicationContextEvent event) {
        SpringContextUtil.setApplicationContext(event.getApplicationContext());
    }
}
