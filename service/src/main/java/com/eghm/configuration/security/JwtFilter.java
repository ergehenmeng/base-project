package com.eghm.configuration.security;

import cn.hutool.core.collection.CollUtil;
import com.eghm.configuration.ManageProperties;
import com.eghm.model.dto.ext.JwtToken;
import com.eghm.service.common.JwtTokenService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wyb
 * @date 2022/1/28 19:07
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtTokenService jwtTokenService;

    private ManageProperties manageProperties;

    @Autowired
    public void setJwtTokenService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Autowired
    public void setManageProperties(ManageProperties manageProperties) {
        this.manageProperties = manageProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(manageProperties.getJwt().getHeader());
        // 判断请求头中是否有token,如果有将用户的id及权限设置进去
        if (header != null && header.startsWith(manageProperties.getJwt().getPrefix())) {
            String token = header.replace(manageProperties.getJwt().getPrefix(), "");
            Optional<JwtToken> jwtToken = jwtTokenService.parseToken(token);
            if (jwtToken.isPresent()) {
                JwtToken jwt = jwtToken.get();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwt.getId(), null, this.authorityList(jwt.getAuthList()));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(request, response);
    }

    private List<GrantedAuthority> authorityList(List<String> authList) {
        if (CollUtil.isEmpty(authList)) {
            return Lists.newArrayList();
        }
        return authList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
