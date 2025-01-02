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
        SystemProperties.ManageProperties properties = systemProperties.getManage();
        return GroupedOpenApi.builder().group("管理端API接口")
                .pathsToMatch("/**")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    if (properties.getToken() == null) {
                        return operation;
                    }
                    SystemProperties.ManageProperties.Token token = properties.getToken();
                    return operation.addParametersItem(new HeaderParameter().name(token.getTokenName()).required(true).schema(new StringSchema()._default(token.getTokenPrefix() + token.getMockToken()).name(token.getTokenName()).description("令牌")));
                })
                .packagesToScan("com.eghm.web.controller").build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("管理端API接口")
                        .version("1.0")
                        .description("管理端接口文档")
                        .termsOfService("https://manage.eghm.top/"));
    }

}
