package com.eghm.interfaces.manage.configuration;

import com.eghm.application.shared.cache.CacheService;
import com.eghm.application.shared.common.CommonService;
import com.eghm.application.shared.common.UserTokenService;
import com.eghm.application.shared.configuration.ApplicationProperties;
import com.eghm.interfaces.core.configuration.WebMvcConfig;
import com.eghm.infrastructure.shared.configuration.data.permission.DataScopeAspect;
import com.eghm.application.system.port.in.SysMenuService;
import com.eghm.interfaces.manage.configuration.filter.AuthFilter;
import com.eghm.interfaces.manage.configuration.interceptor.LockScreenInterceptor;
import com.eghm.interfaces.manage.configuration.interceptor.PermInterceptor;
import com.eghm.interfaces.manage.configuration.interceptor.SubmitIntervalInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static com.eghm.constants.CommonConstant.FILTER_PATTERN;
import static com.eghm.constants.CommonConstant.INTERCEPTOR_PATTERN;
import static com.eghm.constants.CommonConstant.MANAGE_PREFIX;

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
        return new SubmitIntervalInterceptor();
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
