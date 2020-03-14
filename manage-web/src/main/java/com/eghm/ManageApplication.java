package com.eghm;


import com.eghm.configuration.task.config.SystemTaskRegistrar;
import com.eghm.utils.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 程序启动入口
 *
 * @author 二哥很猛
 */
@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy
@ServletComponentScan(basePackages = {"com.eghm.filter", "com.eghm.listener"})
@MapperScan(basePackages = "com.eghm.dao.mapper")
public class ManageApplication implements ApplicationListener<ContextRefreshedEvent>, ApplicationRunner {

    @Autowired
    private SystemTaskRegistrar systemTaskRegistrar;

    public static void main(String[] args) {
        new SpringApplicationBuilder(ManageApplication.class).bannerMode(Banner.Mode.OFF).run(args);
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        SpringContextUtil.setApplicationContext(event.getApplicationContext());
    }

    @Override
    public void run(ApplicationArguments args) {
        systemTaskRegistrar.loadOrRefreshTask();
    }
}
