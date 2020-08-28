package com.eghm.configuration.security;

import com.eghm.common.enums.ErrorCode;
import com.eghm.dao.model.system.SysOperator;
import com.eghm.service.sys.SysMenuService;
import com.eghm.service.sys.SysOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 自定义获取用户信息接口,如果找不到用户信息直接抛异常
 * 如果找到,则组装为UserDetails对象供后续拦截器进行校验
 *
 * @author 二哥很猛
 * @date 2018/1/25 10:00
 */
@Service("userDetailsService")
public class OperatorDetailsServiceImpl implements UserDetailsService {

    private SysOperatorService sysOperatorService;

    private SysMenuService sysMenuService;

    @Autowired
    public void setSysOperatorService(SysOperatorService sysOperatorService) {
        this.sysOperatorService = sysOperatorService;
    }

    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        SysOperator operator = sysOperatorService.getByMobile(username);
        if (operator == null) {
            throw new SystemAuthenticationException(ErrorCode.OPERATOR_NOT_FOUND);
        }
        if (operator.getState() == 0) {
            throw new SystemAuthenticationException(ErrorCode.OPERATOR_LOCKED_ERROR);
        }
        //查询并组织权限信息
        List<GrantedAuthority> authorities = sysMenuService.getAuthorityByOperatorId(operator.getId());
        return new SecurityOperator(operator, authorities);
    }
}
