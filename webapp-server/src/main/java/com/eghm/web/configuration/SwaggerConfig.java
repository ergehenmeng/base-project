package com.eghm.web.configuration;

import com.eghm.configuration.SystemProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import lombok.AllArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 * @author 二哥很猛
 * @since 2019/8/20 10:58
 */
@Configuration
@Profile({"dev", "test"})
@AllArgsConstructor
public class SwaggerConfig {

    private final SystemProperties systemProperties;

    @Bean
    public GroupedOpenApi userApi(){
        SystemProperties.ApiProperties properties = systemProperties.getApi();
        return GroupedOpenApi.builder().group("移动端API接口")
                .pathsToMatch("/**")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.addParametersItem(new HeaderParameter().name("Channel").required(true).description("操作渠道").schema(new StringSchema()._default(properties.getMockChannel().name()).name("Channel").description("操作渠道")));
                    return operation.addParametersItem(new HeaderParameter().name("Token").required(true).description("令牌").schema(new StringSchema()._default(properties.getMockToken()).name("Token").description("令牌")));
                })
                .packagesToScan("com.eghm.web.controller").build();
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("移动端API接口")
                        .version("1.0")
                        .description("移动端接口文档")
                        .termsOfService("https://webapp.eghm.top/"));
    }

}