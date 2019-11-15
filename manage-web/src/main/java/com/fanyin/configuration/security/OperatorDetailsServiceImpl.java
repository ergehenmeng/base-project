package com.fanyin.configuration.security;

import com.fanyin.common.enums.ErrorCode;
import com.fanyin.dao.model.system.SystemOperator;
import com.fanyin.service.system.SystemMenuService;
import com.fanyin.service.system.SystemOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


/**
 * 自定义获取用户信息接口,如果找不到用户信息直接抛异常
 * 如果找到,则组装为UserDetails对象供后续拦截器进行校验
 * @author 二哥很猛
 * @date 2018/1/25 10:00
 */
public class OperatorDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SystemOperatorService systemOperatorService;

    @Autowired
    private SystemMenuService systemMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemOperator operator = systemOperatorService.getByMobile(username);
        if(operator == null){
            throw new SystemAuthenticationException(ErrorCode.OPERATOR_NOT_FOUND);
        }
        if(operator.getState() == 0){
            throw new SystemAuthenticationException(ErrorCode.OPERATOR_LOCKED_ERROR);
        }
        //查询并组织权限信息
        List<GrantedAuthority> authorities = systemMenuService.getAuthorityByOperatorId(operator.getId());
        return new SecurityOperator(operator,authorities);
    }
}
