package com.eghm.web.configuration;

import com.eghm.constants.ApplicationHeader;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder().group("移动端API接口")
                .pathsToMatch("/**")
                .addOperationCustomizer((operation, handlerMethod) ->
                        operation.addParametersItem(new HeaderParameter().name(ApplicationHeader.CHANNEL).required(true).description("操作渠道"))
                        .addParametersItem(new HeaderParameter().name(ApplicationHeader.TOKEN).required(true).description("令牌")))
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
