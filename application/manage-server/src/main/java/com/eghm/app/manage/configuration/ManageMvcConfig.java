package com.eghm.app.manage.configuration;

import com.eghm.foundation.cache.service.CacheService;
import com.eghm.platform.config.service.CommonService;
import com.eghm.platform.iam.service.UserTokenService;
import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.web.config.WebMvcConfig;
import com.eghm.foundation.data.config.data.permission.DataScopeAspect;
import com.eghm.platform.iam.service.SysMenuService;
import com.eghm.app.manage.configuration.filter.AuthFilter;
import com.eghm.app.manage.configuration.interceptor.LockScreenInterceptor;
import com.eghm.app.manage.configuration.interceptor.PermInterceptor;
import com.eghm.app.manage.configuration.interceptor.SubmitIntervalInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static com.eghm.foundation.core.constants.CommonConstant.FILTER_PATTERN;
import static com.eghm.foundation.core.constants.CommonConstant.INTERCEPTOR_PATTERN;
import static com.eghm.foundation.core.constants.CommonConstant.MANAGE_PREFIX;

/**
 * mvc配置信息
 *
 * @author 二哥很猛
 * @since 2018/1/18 18:35
 */
@Configuration
public class ManageMvcConfig extends WebMvcConfig {

    private final CacheService cacheService;

    private final CommonService commonService;

    private final SysMenuService sysMenuService;

    private final UserTokenService userTokenService;

    public ManageMvcConfig(ObjectMapper objectMapper, ApplicationProperties applicationProperties, UserTokenService userTokenService,
                           SysMenuService sysMenuService, CacheService cacheService, @Qualifier("taskExecutor") TaskExecutor taskExecutor,
                           CommonService commonService) {
        super(objectMapper, taskExecutor, applicationProperties);
        this.cacheService = cacheService;
        this.sysMenuService = sysMenuService;
        this.userTokenService = userTokenService;
        this.commonService = commonService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] whiteList = applicationProperties.getManage().getWhiteList();
        registry.addInterceptor(submitIntervalInterceptor());
        registry.addInterceptor(permInterceptor()).addPathPatterns(MANAGE_PREFIX + INTERCEPTOR_PATTERN).excludePathPatterns(whiteList);
        registry.addInterceptor(lockScreenInterceptor()).excludePathPatterns(whiteList);
    }

    /**
     * 提交间隔限制
     */
    @Bean
    public HandlerInterceptor submitIntervalInterceptor() {
        return new SubmitIntervalInterceptor(cacheService);
    }

    /**
     * 按钮权限
     */
    @Bean
    public PermInterceptor permInterceptor() {
        return new PermInterceptor(commonService, sysMenuService);
    }

    /**
     * 按钮权限
     */
    @Bean
    public LockScreenInterceptor lockScreenInterceptor() {
        return new LockScreenInterceptor(cacheService);
    }

    /**
     * 数据权限,必须在manage-web中声明为bean
     */
    @Bean
    public DataScopeAspect dataScopeAspect() {
        
        return new DataScopeAspect();
    }

    /**
     * 登录校验
     */
    @Bean("authFilter")
    public FilterRegistrationBean<AuthFilter> authFilter() {
        ApplicationProperties.ManageProperties manage = applicationProperties.getManage();
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        AuthFilter requestFilter = new AuthFilter(userTokenService, manage);
        requestFilter.exclude(manage.getWhiteList());
        registrationBean.setFilter(requestFilter);
        registrationBean.addUrlPatterns(MANAGE_PREFIX + FILTER_PATTERN);
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
        registrationBean.setOrder(Integer.MIN_VALUE + 5);
        return registrationBean;
    }

}
