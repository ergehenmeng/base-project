package com.eghm.web.configuration;

import com.eghm.cache.CacheService;
import com.eghm.common.UserTokenService;
import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.WebMvcConfig;
import com.eghm.configuration.data.permission.DataScopeAspect;
import com.eghm.service.sys.SysMenuService;
import com.eghm.web.configuration.filter.AuthFilter;
import com.eghm.web.configuration.interceptor.LockScreenInterceptor;
import com.eghm.web.configuration.interceptor.PermInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * mvc配置信息
 *
 * @author 二哥很猛
 * @since 2018/1/18 18:35
 */
@Configuration
public class ManageMvcConfig extends WebMvcConfig {

    private final CacheService cacheService;

    private final SysMenuService sysMenuService;

    private final UserTokenService userTokenService;

    public ManageMvcConfig(ObjectMapper objectMapper, SystemProperties systemProperties, UserTokenService userTokenService,
                           SysMenuService sysMenuService, CacheService cacheService, @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        super(objectMapper, taskExecutor, systemProperties);
        this.cacheService = cacheService;
        this.sysMenuService = sysMenuService;
        this.userTokenService = userTokenService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] whiteList = systemProperties.getManage().getWhiteList();
        registry.addInterceptor(permInterceptor()).excludePathPatterns(whiteList);
        registry.addInterceptor(lockScreenInterceptor()).excludePathPatterns(whiteList);
    }

    /**
     * 按钮权限
     */
    @Bean
    public PermInterceptor permInterceptor() {
        return new PermInterceptor(sysMenuService);
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
        SystemProperties.ManageProperties manage = systemProperties.getManage();
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        AuthFilter requestFilter = new AuthFilter(userTokenService, manage);
        requestFilter.exclude(manage.getWhiteList());
        registrationBean.setFilter(requestFilter);
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setOrder(Integer.MIN_VALUE + 5);
        return registrationBean;
    }

}
