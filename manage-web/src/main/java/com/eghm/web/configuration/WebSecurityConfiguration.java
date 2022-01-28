package com.eghm.web.configuration;

import com.eghm.configuration.ApiProperties;
import com.eghm.configuration.data.permission.DataScopeAspect;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.configuration.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring security权限配置
 *
 * @author 二哥很猛
 * @date 2018/1/25 09:35
 */

@Configuration
@EnableConfigurationProperties({WebMvcProperties.class, ApiProperties.class})
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private ApiProperties applicationProperties;

	private WebMvcProperties webMvcProperties;

	private Encoder encoder;

    private JwtFilter jwtFilter;

    @Autowired
    public void setJwtFilter(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Autowired
	public void setApplicationProperties(ApiProperties apiProperties) {
		this.applicationProperties = apiProperties;
	}

	@Autowired
	public void setWebMvcProperties(WebMvcProperties webMvcProperties) {
		this.webMvcProperties = webMvcProperties;
	}

	@Autowired
	public void setEncoder(Encoder encoder) {
		this.encoder = encoder;
	}

	/**
	 * 此处通过Bean方式声明,因为manage与api共同依赖了service包
	 */
	@Bean("userDetailsService")
	public UserDetailsService detailsService() {
		return new OperatorDetailsServiceImpl();
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return detailsService();
	}


    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(webMvcProperties.getStaticPathPattern());
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
		http.authorizeRequests()
				.antMatchers(applicationProperties.getIgnoreUrl()).permitAll()
				.antMatchers(applicationProperties.getLoginIgnoreUrl()).fullyAuthenticated()
				.anyRequest().authenticated();
		// 权限拦截
		http.addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling()
				// 默认访问拒绝由AccessDeniedHandlerImpl(重定向)处理.手动实现返回前台为json
				.accessDeniedHandler(accessDeniedHandler());

	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}

	/**
	 * 登陆校验器
	 *
	 * @return bean
	 */
	private AuthenticationProvider authenticationProvider() {
		CustomAuthenticationProvider provider = new CustomAuthenticationProvider();
		//屏蔽原始错误异常
		provider.setHideUserNotFoundExceptions(false);
		provider.setUserDetailsService(userDetailsService());
		provider.setEncoder(encoder);
		return provider;
	}

	/**
	 * 登陆成功后置处理
	 *
	 * @return bean
	 */
	private LoginSuccessHandler loginSuccessHandler() {
		return new LoginSuccessHandler();
	}

	/**
	 * 退出登陆
	 *
	 * @return bean
	 */
	private LogoutSuccessHandler logoutSuccessHandler() {
		return new LogoutSuccessHandler();
	}

	/**
	 * 登陆失败后置处理
	 *
	 * @return bean
	 */
	private LoginFailureHandler loginFailureHandler() {
		return new LoginFailureHandler();
	}


	/**
	 * 权限管理过滤器,
	 * 声明为Bean会加入到全局FilterChain中拦截所有请求
	 * 不声明Bean默认会在FilterChainProxy子调用链中按条件执行,减少不必要执行逻辑
	 *
	 * @return bean
	 */
	private CustomFilterSecurityInterceptor filterSecurityInterceptor() {
		CustomFilterSecurityInterceptor interceptor = new CustomFilterSecurityInterceptor(metadataSource());
		interceptor.setAccessDecisionManager(accessDecisionManager());
		return interceptor;
	}


	private CustomAccessDecisionManager accessDecisionManager() {
		return new CustomAccessDecisionManager();
	}


	/**
	 * 角色权限资源管理
	 *
	 * @return bean
	 */
	@Bean
	public CustomFilterInvocationSecurityMetadataSource metadataSource() {
		return new CustomFilterInvocationSecurityMetadataSource();
	}

	/**
	 * 数据权限,必须在manage-web中声明为bean
	 */
	@Bean
	public DataScopeAspect dataScopeAspect() {
		return new DataScopeAspect();
	}

	/**
	 * 附加信息管理
	 *
	 * @return bean
	 */
	private CustomAuthenticationDetailsSource detailsSource() {
		return new CustomAuthenticationDetailsSource();
	}


	/**
	 * 权限不足
	 *
	 * @return bean
	 */
	private AccessDeniedHandler accessDeniedHandler() {
		return new FailureAccessDeniedHandler();
	}

}
