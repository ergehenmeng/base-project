package com.fanyin.configuration.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

/**
 * 决策(访问权限)管理器,用来判断用户是否有访问该资源的权限
 * @author 二哥很猛
 * @date 2018/1/25 11:39
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //可能是公共页面访问
        if(authentication == null || configAttributes == null || configAttributes.size() <= 0){
            return;
        }
        for (ConfigAttribute attribute : configAttributes){
            String role = attribute.getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()){
                if (role.equals(ga.getAuthority())){
                    return ;
                }
            }
        }
        throw new AccessDeniedException("非法权限访问");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class == clazz;
    }
}
