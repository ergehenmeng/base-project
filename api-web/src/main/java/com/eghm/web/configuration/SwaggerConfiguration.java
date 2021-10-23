package com.eghm.web.configuration;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.PluginRegistrySupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.operation.OperationParameterReader;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;


/**
 * @author 二哥很猛
 * @date 2019/8/20 10:58
 */
@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfiguration implements ApplicationContextAware {


    private ApplicationContext applicationContext;

    @Bean
    public Docket docket() {
        Contact contact = new Contact("二哥很猛", "https://github.com/ergehenmeng/", "664956140@qq.com");
        ApiInfo sysApiInfo = new ApiInfoBuilder()
                .title("移动端API接口")
                .description("针对移动端开发基础接口文档")
                .termsOfServiceUrl("http://www.group.com/")
                .contact(contact)
                .version("v0.78")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(sysApiInfo)
                .consumes(Sets.newHashSet("application/json"))
                .groupName("移动端API接口")
                .select()
                .apis(basePackage("com.eghm.web.controller"))
                .paths(PathSelectors.any())
                .build();
        removePlugin();
        return docket;
    }

    private void removePlugin() {
        PluginRegistry<OperationBuilderPlugin, DocumentationType> pluginRegistry =
                applicationContext.getBean("operationBuilderPluginRegistry", PluginRegistry.class);
        // 插件集合
        List<OperationBuilderPlugin> plugins = pluginRegistry.getPlugins();
        // 从spring容器中获取需要删除的插件
        OperationParameterReader operationParameterReader = applicationContext.getBean(OperationParameterReader.class);
        // 原plugins集合不能修改，创建新集合，通过反射替换
        if (pluginRegistry.contains(operationParameterReader)) {
            List<OperationBuilderPlugin> pluginsNew = new ArrayList<>(plugins);
            pluginsNew.remove(operationParameterReader);
            try {
                Field field = PluginRegistrySupport.class.getDeclaredField("plugins");
                field.setAccessible(true);
                field.set(pluginRegistry, pluginsNew);
            } catch (Exception e) {
                log.error("swagger插件移除异常", e);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
