package com.eghm.web.configuration;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;


/**
 * @author 二哥很猛
 * @date 2019/8/20 10:58
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {


    @Bean
    public Docket docket() {
        Contact contact = new Contact("二哥很猛", "https://github.com/ergehenmeng/", "664956140@qq.com");
        ApiInfo sysApiInfo = new ApiInfoBuilder()
                .title("移动端API接口")
                .description("针对移动端开发基础接口文档")
                .termsOfServiceUrl("http://www.eghm.top/")
                .contact(contact)
                .version("v0.78")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(sysApiInfo)
                .consumes(Sets.newHashSet("application/json"))
                .groupName("移动端API接口")
                .select()
                .apis(basePackage("com.eghm.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }

}
