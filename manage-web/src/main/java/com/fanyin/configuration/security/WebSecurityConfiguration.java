package com.fanyin.configuration.security;

import com.fanyin.configuration.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.StringUtils;

/**
 * spring security权限配置
 * @author 二哥很猛
 * @date 2018/1/25 09:35
 */

@Configuration
@EnableConfigurationProperties({WebMvcProperties.class,ApplicationProperties.class})
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{


    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private WebMvcProperties webMvcProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    protected UserDetailsService userDetailsService() {
        return detailsService();
    }

    @Bean("userDetailsService")
    public UserDetailsService detailsService(){
        return new OperatorDetailsServiceImpl();
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers(webMvcProperties.getStaticPathPattern());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Iframe同一域名内可以访问
        http.headers().frameOptions().sameOrigin();

        http
             .authorizeRequests()
                .antMatchers(StringUtils.tokenizeToStringArray(applicationProperties.getIgnoreUrl(),";")).permitAll()
                .antMatchers(StringUtils.tokenizeToStringArray(applicationProperties.getLoginIgnoreUrl(),";")).fullyAuthenticated()
                .anyRequest().authenticated()
                .and()
             .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                .usernameParameter("mobile")
                .passwordParameter("password")
                .authenticationDetailsSource(detailsSource())
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailureHandler())
                .and()
             .logout()
                .logoutSuccessHandler(logoutSuccessHandler())
                .permitAll()
                .invalidateHttpSession(true);
        http.sessionManagement().maximumSessions(1).expiredUrl("/index");
        //权限拦截
        http
                .addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class)
                .csrf()
                .disable()
                //默认访问拒绝由AccessDeniedHandlerImpl处理.手动实现返回前台为json
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * 登陆校验器
     * @return bean
     */
    private AuthenticationProvider authenticationProvider(){
        CustomAuthenticationProvider provider = new CustomAuthenticationProvider();
        //屏蔽原始错误异常
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService());
        provider.setEncoder(passwordEncoder);
        return provider;
    }
    /**
     * 登陆成功后置处理
     * @return bean
     */
    private LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }

    /**
     * 退出登陆
     * @return bean
     */
    private LogoutSuccessHandler logoutSuccessHandler(){
        return new LogoutSuccessHandler();
    }

    /**
     * 登陆失败后置处理
     * @return bean
     */
    private LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler();
    }


    /**
     * 权限管理过滤器,
     * 声明为Bean会加入到全局FilterChain中拦截所有请求
     * 不声明Bean默认会在FilterChainProxy子调用链中按条件执行,减少不必要执行逻辑
     * @return bean
     */
    private CustomFilterSecurityInterceptor filterSecurityInterceptor(){
        CustomFilterSecurityInterceptor interceptor = new CustomFilterSecurityInterceptor(metadataSource());
        interceptor.setAccessDecisionManager(accessDecisionManager());
        return interceptor;
    }


    private CustomAccessDecisionManager accessDecisionManager(){
        return new CustomAccessDecisionManager();
    }


    /**
     * 角色权限资源管理
     * @return bean
     */
    @Bean
    public CustomFilterInvocationSecurityMetadataSource metadataSource(){
        return new CustomFilterInvocationSecurityMetadataSource();
    }

    /**
     * 附加信息管理
     * @return bean
     */
    private CustomAuthenticationDetailsSource detailsSource(){
        return new CustomAuthenticationDetailsSource();
    }


    /**
     * 权限不足
     * @return bean
     */
    private AccessDeniedHandler accessDeniedHandler(){
        return new FailureAccessDeniedHandler();
    }

}
