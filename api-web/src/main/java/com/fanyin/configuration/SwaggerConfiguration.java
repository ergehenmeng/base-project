package com.fanyin.configuration;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author 二哥很猛
 * @date 2019/8/20 10:58
 */
@Configuration
@EnableSwagger2
@PropertySource(value = "classpath:swagger.properties")
public class SwaggerConfiguration {


    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .produces(Sets.newHashSet("application/json"))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fanyin.controller"))
                .build();
    }


    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("移动端接口文档")
                .version("1.0.0")
                .build();
    }

}
