package com.eghm.configuration;

import com.eghm.configuration.security.BcEncoder;
import com.eghm.configuration.security.Encoder;
import com.eghm.configuration.template.FreemarkerHtmlTemplate;
import com.eghm.configuration.template.HtmlTemplate;
import com.eghm.constants.SystemConstant;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;

/**
 * 公用配置,所有web模块所公用的配置信息均可在该配置文件中声明
 *
 * @author 二哥很猛
 * @date 2018/9/13 11:19
 */
@EnableConfigurationProperties({ApplicationProperties.class})
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private ApplicationProperties applicationProperties;

    /**
     * 图形验证码
     *
     * @return bean
     */
    @Bean("producer")
    public DefaultKaptcha captcha() {
        DefaultKaptcha captcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty(Constants.KAPTCHA_BORDER, "no");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "125");
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "45");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "5");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "abcdefhkmnprstwxy23456789ABCEFGHGKMNPRSTWXY");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "宋体");
        properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_IMPL, "com.eghm.configuration.CaptchaProducer");
        Config config = new Config(properties);
        captcha.setConfig(config);
        return captcha;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/**").addResourceLocations("file:///" + applicationProperties.getUploadDir() + SystemConstant.DEFAULT_PATTERN);
    }

    /**
     * url中如果包含 "." 默认情况下后面的会被截取,将参数设置为false则会全部匹配
     *
     * @param configurer configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseRegisteredSuffixPatternMatch(false);
    }

    /**
     * 密码加密bean 独立于spring-security之外的工具类
     *
     * @return bean
     */
    @Bean
    public Encoder encoder() {
        return new BcEncoder();
    }

    /**
     * html模板渲染
     *
     * @return bean
     */
    @Bean
    public HtmlTemplate htmlTemplate() {
        return new FreemarkerHtmlTemplate();
    }

}
