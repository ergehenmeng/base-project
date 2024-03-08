package com.eghm.web.configuration;

import com.eghm.configuration.SystemProperties;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;


/**
 * @author 二哥很猛
 * @since 2019/8/20 10:58
 */
@Configuration
@EnableSwagger2WebMvc
@Profile({"dev", "test"})
@AllArgsConstructor
@EnableConfigurationProperties(SystemProperties.class)
public class SwaggerConfig {

    private final SystemProperties systemProperties;

    @Bean
    public Docket docket() {
        Contact contact = new Contact("二哥很猛", "https://github.com/ergehenmeng/", "664956140@qq.com");
        ApiInfo sysApiInfo = new ApiInfoBuilder()
                .title("移动端API接口")
                .description("针对移动端开发基础接口文档")
                .termsOfServiceUrl("https://www.eghm.top/")
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
                .build().globalOperationParameters(setParameterList());
    }

    /**
     * 设置默认token
     *
     * @return 请求头信息
     */
    private List<Parameter> setParameterList() {
        ParameterBuilder token = new ParameterBuilder();
        List<Parameter> params = new ArrayList<>();
        SystemProperties.ApiProperties properties = systemProperties.getApi();
        String header = "header";
        String headerType = "string";
        token.name("Channel").description("操作渠道").modelRef(new ModelRef(headerType)).parameterType(header).required(true).defaultValue(properties.getMockChannel().name()).build();
        token.name("Token").description("令牌").modelRef(new ModelRef(headerType)).parameterType(header).required(true).defaultValue(properties.getMockToken()).build();
        params.add(token.build());
        return params;
    }

}
