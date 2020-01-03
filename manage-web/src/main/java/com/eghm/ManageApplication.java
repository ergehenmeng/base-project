package com.eghm;


import com.eghm.utils.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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
public class ManageApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(ManageApplication.class).bannerMode(Banner.Mode.OFF).run(args);
        SpringContextUtil.setApplicationContext(applicationContext);
    }
}
