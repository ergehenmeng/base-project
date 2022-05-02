package com.eghm.web.configuration;

import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.data.permission.DataScopeAspect;
import com.eghm.configuration.security.*;
import com.eghm.service.common.JwtTokenService;
import com.eghm.service.sys.SysMenuService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring security权限配置
 *
 * @author 二哥很猛
 * @date 2018/1/25 09:35
 */

@Configuration
@EnableConfigurationProperties({WebMvcProperties.class, SystemProperties.class})
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final SystemProperties systemProperties;

    private final JwtTokenService jwtTokenService;

    private final SysMenuService sysMenuService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

        SystemProperties.ManageProperties manageProperties = systemProperties.getManage();

        http.authorizeRequests()
				.antMatchers(manageProperties.getSecurity().getIgnore()).permitAll()
				.antMatchers(manageProperties.getSecurity().getIgnoreAuth()).fullyAuthenticated()
				.anyRequest().authenticated();
		// 认证拦截及权限拦截
		http.addFilterBefore(new JwtFilter(jwtTokenService, systemProperties), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class);

        http.exceptionHandling()
                // 认证失败
                .authenticationEntryPoint(new AuthenticationFailHandler())
				// 权限不足
				.accessDeniedHandler(new FailAccessDeniedHandler());
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
		return new CustomFilterInvocationSecurityMetadataSource(sysMenuService);
	}

	/**
	 * 数据权限,必须在manage-web中声明为bean
	 */
	@Bean
	public DataScopeAspect dataScopeAspect() {
		return new DataScopeAspect();
	}

}
