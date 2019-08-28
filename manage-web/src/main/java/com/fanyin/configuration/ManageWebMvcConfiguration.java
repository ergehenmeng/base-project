package com.fanyin.configuration;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * mvc配置信息
 * @author 二哥很猛
 * @date 2018/1/18 18:35
 */
@Configuration
@EnableConfigurationProperties({ApplicationProperties.class, ServerProperties.class})
public class ManageWebMvcConfiguration extends WebMvcConfiguration {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ServerProperties serverProperties;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //如果采用nginx进行静态资源转发,则不需要该配置
        registry.addResourceHandler("/upload/**").addResourceLocations( "file:///" + applicationProperties.getUploadDir() + AbstractUpload.DEFAULT_DIR);
    }


//    @Bean
//    public ErrorPageController errorPageController(ErrorAttributes errorAttributes, ObjectProvider<List<ErrorViewResolver>> errorViewResolversProvider){
//        return new ErrorPageController(errorAttributes,errorViewResolversProvider.getIfAvailable(),serverProperties.getError());
//    }

    /**
     * 将所有的链接由8081 转到 8080
     * @return 连接器
     */
    @Bean
    public Connector connector(){
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8081);
        connector.setSecure(false);
        connector.setRedirectPort(8080);
        return connector;
    }
}
