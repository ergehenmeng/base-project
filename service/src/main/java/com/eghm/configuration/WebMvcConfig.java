package com.eghm.configuration;

import cn.hutool.core.util.StrUtil;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.common.impl.DefaultAlarmServiceImpl;
import com.eghm.common.impl.DingTalkAlarmServiceImpl;
import com.eghm.common.impl.FeiShuAlarmServiceImpl;
import com.eghm.configuration.encoder.BcEncoder;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.configuration.jackson.DesensitizationAnnotationInterceptor;
import com.eghm.configuration.log.LogTraceFilter;
import com.eghm.constants.CommonConstant;
import com.eghm.convertor.DateAnnotationFormatterBinderFactory;
import com.eghm.convertor.Knife4jEnumBinderConverterFactory;
import com.eghm.convertor.YuanToCentAnnotationFormatterBinderFactory;
import com.eghm.enums.AlarmType;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.impl.NoNoise;
import com.google.code.kaptcha.impl.WaterRipple;
import com.google.code.kaptcha.util.Config;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 公用配置,所有web模块所公用的配置信息均可在该配置文件中声明
 * {@link AsyncConfigurer } 用来指定指定异步线程池和异常处理器
 * @author 二哥很猛
 * @since 2018/9/13 11:19
 */
@Slf4j
@AllArgsConstructor
@EnableConfigurationProperties({SystemProperties.class})
public class WebMvcConfig implements WebMvcConfigurer, AsyncConfigurer {

    private final ObjectMapper objectMapper;

    private final TaskExecutor taskExecutor;

    protected final SystemProperties systemProperties;

    @Bean
    public static BeanPostProcessor springFoxBeanPostProcessor() {
        return new SpringFoxBeanPostProcessor();
    }

    /**
     * 设置校验为快速失败
     */
    @Bean
    public static LocalValidatorFactoryBean defaultValidator(ApplicationContext applicationContext) {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory(applicationContext);
        factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
        factoryBean.getValidationPropertyMap().put(HibernateValidatorConfiguration.FAIL_FAST, "true");
        return factoryBean;
    }

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
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "120");
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "45");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "35");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "4");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "abcdefhkmnprstwxy2345678ABCEFGHGKMNPRSTWXY");
        properties.setProperty(Constants.KAPTCHA_NOISE_IMPL, NoNoise.class.getName());
        properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, WaterRipple.class.getName());
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_IMPL, systemProperties.getManage().getCaptchaType().getName());
        Config config = new Config(properties);
        captcha.setConfig(config);
        return captcha;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/**").addResourceLocations("file:///" + systemProperties.getUploadPath() + CommonConstant.ROOT_FOLDER);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.error("@Async接口调用异常: [{}] [{}]", method.getName(), params, ex);
    }

    @Override
    public Executor getAsyncExecutor() {
        return TtlExecutors.getTtlExecutor(taskExecutor);
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
     * 如果对象为空则返回空串 而不是 null
     * 如果对象空数组或集合 则返回[]
     * 该功能比较鸡肋,最好在开发时约定好格式
     */
    @PostConstruct
    public void jsonNullToString() {
        JsonSerializer<Object> arraySerializer = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartArray();
                gen.writeEndArray();
            }
        };
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new BeanSerializerModifier() {
            @Override
            public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
                for (BeanPropertyWriter writer : beanProperties) {
                    JavaType javaType = writer.getType();
                    if (javaType.isArrayType() || javaType.isCollectionLikeType()) {
                        writer.assignNullSerializer(arraySerializer);
                    }
                }
                return beanProperties;
            }
        }));
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        AnnotationIntrospector ai = objectMapper.getSerializationConfig().getAnnotationIntrospector();
        AnnotationIntrospector newAi = AnnotationIntrospectorPair.pair(ai, new DesensitizationAnnotationInterceptor());
        objectMapper.setAnnotationIntrospector(newAi);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new Knife4jEnumBinderConverterFactory());
        registry.addFormatterForFieldAnnotation(new YuanToCentAnnotationFormatterBinderFactory());
        registry.addFormatterForFieldAnnotation(new DateAnnotationFormatterBinderFactory());
    }

    /**
     * 日志追踪过滤器
     */
    @Bean("logTraceFilter")
    public FilterRegistrationBean<LogTraceFilter> logTraceFilter() {
        FilterRegistrationBean<LogTraceFilter> registrationBean = new FilterRegistrationBean<>();
        LogTraceFilter requestFilter = new LogTraceFilter();
        registrationBean.setFilter(requestFilter);
        registrationBean.setOrder(Integer.MIN_VALUE + 1);
        return registrationBean;
    }

    @Bean
    public AlarmService alarmService(JsonService jsonService) {
        SystemProperties.AlarmMsg alarmMsg = systemProperties.getAlarmMsg();
        if (alarmMsg.getAlarmType() == AlarmType.DEFAULT) {
            return new DefaultAlarmServiceImpl();
        }
        if (StrUtil.isNotBlank(alarmMsg.getWebHook())) {
            throw new BusinessException(ErrorCode.WEB_HOOK_NULL);
        }
        if (alarmMsg.getAlarmType() == AlarmType.DING_TALK) {
            return new DingTalkAlarmServiceImpl(jsonService, systemProperties);
        }
        if (alarmMsg.getAlarmType() == AlarmType.FEI_SHU) {
            return new FeiShuAlarmServiceImpl(jsonService, systemProperties);
        }
        return new DefaultAlarmServiceImpl();
    }

}
