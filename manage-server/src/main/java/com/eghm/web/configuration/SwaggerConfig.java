package com.eghm.web.configuration;

import com.eghm.configuration.SystemProperties;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
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
public class SwaggerConfig {

    private final SystemProperties systemProperties;

    @Bean
    public Docket docket() {
        Contact contact = new Contact("二哥很猛", "https://github.com/ergehenmeng/", "664956140@qq.com");
        ApiInfo sysApiInfo = new ApiInfoBuilder()
                .title("管理后台API接口")
                .description("针对管理后台开发基础接口文档")
                .termsOfServiceUrl("https://www.eghm.top/")
                .contact(contact)
                .version("v0.78")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(sysApiInfo)
                .consumes(Sets.newHashSet("application/json"))
                .groupName("管理后台API接口")
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
        List<Parameter> params = new ArrayList<>();
        SystemProperties.ManageProperties.Token token = systemProperties.getManage().getToken();
        if (token.getMockToken() == null) {
            return params;
        }
        ParameterBuilder tokenParameter = new ParameterBuilder();
        tokenParameter.name(token.getTokenName())
                .description("令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .defaultValue(token.getTokenPrefix() + token.getMockToken())
                .build();
        params.add(tokenParameter.build());
        return params;
    }

}
