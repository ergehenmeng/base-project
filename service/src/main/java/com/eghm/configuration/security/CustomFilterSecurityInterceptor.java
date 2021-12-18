package com.eghm.configuration.security;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;

/**
 * 权限管理过滤器
 *
 * @author 二哥很猛
 * @date 2018/1/25 10:46
 */
public class CustomFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private final FilterInvocationSecurityMetadataSource securityMetadataSource;

    public CustomFilterSecurityInterceptor(FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
        this.securityMetadataSource = filterInvocationSecurityMetadataSource;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation invocation = new FilterInvocation(request, response, chain);
        invoke(invocation);
    }

    private void invoke(FilterInvocation invocation) throws IOException, ServletException {
        InterceptorStatusToken token = super.beforeInvocation(invocation);
        try {
            invocation.getChain().doFilter(invocation.getRequest(), invocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return securityMetadataSource;
    }
}
