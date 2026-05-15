package com.eghm.i18n.config;

import com.eghm.i18n.interceptor.LanguageInterceptor;
import com.eghm.i18n.interpolator.ValidatorMessageInterpolator;
import com.eghm.i18n.provider.DefaultI18nMessageProvider;
import com.eghm.i18n.provider.I18nMessageProvider;
import org.hibernate.validator.BaseHibernateValidatorConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
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
    public ValidatorMessageInterpolator dictMessageInterpolator(ObjectProvider<I18nMessageProvider> messageProvider) {
        I18nMessageProvider provider = messageProvider.getIfAvailable();
        if (provider == null) {
            provider = new DefaultI18nMessageProvider();
        }
        return new ValidatorMessageInterpolator(provider);
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(ValidatorMessageInterpolator dictMessageInterpolator) {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setMessageInterpolator(dictMessageInterpolator);
        factoryBean.getValidationPropertyMap().put(BaseHibernateValidatorConfiguration.FAIL_FAST, "true");
        return factoryBean;
    }
}
