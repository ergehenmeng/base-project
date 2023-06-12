package com.eghm.web.configuration;

import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.WebMvcConfig;
import com.eghm.configuration.data.permission.DataScopeAspect;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.JwtTokenService;
import com.eghm.service.sys.SysMenuService;
import com.eghm.web.configuration.filter.AuthFilter;
import com.eghm.web.configuration.filter.LockScreenFilter;
import com.eghm.web.configuration.interceptor.PermInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.DispatcherType;

/**
 * mvc配置信息
 *
 * @author 二哥很猛
 * @date 2018/1/18 18:35
 */
@Configuration
public class ManageMvcConfig extends WebMvcConfig {

    private final JwtTokenService jwtTokenService;

    private final SysMenuService sysMenuService;

    private final CacheService cacheService;

    public ManageMvcConfig(ObjectMapper objectMapper, SystemProperties systemProperties, JwtTokenService jwtTokenService, SysMenuService sysMenuService, CacheService cacheService) {
        super(objectMapper, systemProperties);
        this.jwtTokenService = jwtTokenService;
        this.sysMenuService = sysMenuService;
        this.cacheService = cacheService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        SystemProperties.ManageProperties.Security security = systemProperties.getManage().getSecurity();
        registry.addInterceptor(permInterceptor()).excludePathPatterns(security.getIgnoreAuth()).excludePathPatterns(security.getIgnore());
    }

    /**
     * 按钮权限
     */
    @Bean
    public PermInterceptor permInterceptor() {
        return new PermInterceptor(sysMenuService);
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
        AuthFilter requestFilter = new AuthFilter(manage, jwtTokenService);
        requestFilter.exclude(manage.getSecurity().getIgnore());
        registrationBean.setFilter(requestFilter);
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setOrder(Integer.MIN_VALUE + 5);
        return registrationBean;
    }

    @Bean("lockScreenFilter")
    public FilterRegistrationBean<LockScreenFilter> lockScreenFilter() {
        SystemProperties.ManageProperties manage = systemProperties.getManage();
        FilterRegistrationBean<LockScreenFilter> registrationBean = new FilterRegistrationBean<>();
        LockScreenFilter requestFilter = new LockScreenFilter(cacheService);
        requestFilter.exclude(manage.getSecurity().getIgnore());
        registrationBean.setFilter(requestFilter);
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setOrder(Integer.MIN_VALUE + 10);
        return registrationBean;
    }
}
