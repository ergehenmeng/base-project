package com.eghm.web.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import lombok.AllArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.eghm.constants.ApplicationHeader.TOKEN;

/**
 * @author 二哥很猛
 * @since 2019/8/20 10:58
 */
@Configuration
@AllArgsConstructor
@Profile({"dev", "test"})
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder().group("管理端API接口")
                .pathsToMatch("/**")
                .addOperationCustomizer((operation, handlerMethod) -> operation.addParametersItem(new HeaderParameter().name(TOKEN).required(true).description("令牌")))
                .packagesToScan("com.eghm.web.controller").build();
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("管理端API接口")
                        .version("1.0")
                        .description("管理端接口文档")
                        .termsOfService("https://manage.eghm.top/"));
    }

}
