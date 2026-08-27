package com.eghm.i18n.config;

import cn.hutool.extra.spring.EnableSpringUtil;
import com.eghm.i18n.interceptor.LanguageInterceptor;
import com.eghm.i18n.interceptor.RespBodyI18nAdviceHandler;
import com.eghm.i18n.interpolator.ValidatorMessageInterpolator;
import com.eghm.i18n.provider.I18nMessageProvider;
import com.eghm.i18n.serializer.TranslationSerializer;
import jakarta.validation.MessageInterpolator;
import org.hibernate.validator.BaseHibernateValidatorConfiguration;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 国际化自动配置类
 * @author wyb-eghm
 * @since 2026/5/15
 */
@AutoConfiguration
@EnableSpringUtil
@EnableConfigurationProperties(I18nProperties.class)
@ConditionalOnProperty(prefix = "i18n", name = "enabled", havingValue = "true", matchIfMissing = true)
public class I18nAutoConfiguration implements WebMvcConfigurer {

    private final I18nProperties i18nProperties;

    public I18nAutoConfiguration(I18nProperties i18nProperties) {
        this.i18nProperties = i18nProperties;
    }

    @Bean
    public LanguageInterceptor languageInterceptor() {
        return new LanguageInterceptor(i18nProperties);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(languageInterceptor()).order(Integer.MIN_VALUE);
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageInterpolator messageInterpolator(ObjectProvider<I18nMessageProvider> provider, MessageSource messageSource) {
        I18nMessageProvider messageProvider = provider.getIfAvailable();
        ResourceBundleMessageInterpolator messageInterpolator = new ResourceBundleMessageInterpolator(new MessageSourceResourceBundleLocator(messageSource));
        if (messageProvider == null) {
            return messageInterpolator;
        } else {
            TranslationSerializer.setMessageProvider(messageProvider);
            return new ValidatorMessageInterpolator(messageProvider,  messageInterpolator);
        }
    }
    
    @Bean
    public RespBodyI18nAdviceHandler respBodyAdviceHandler(ObjectProvider<I18nMessageProvider> provider) {
        return new RespBodyI18nAdviceHandler(provider.getIfAvailable());
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(MessageInterpolator messageInterpolator) {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setMessageInterpolator(messageInterpolator);
        factoryBean.getValidationPropertyMap().put(BaseHibernateValidatorConfiguration.FAIL_FAST, "true");
        return factoryBean;
    }
}
