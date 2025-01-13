package com.eghm.web.configuration.filter;

import com.eghm.enums.ErrorCode;
import com.eghm.service.sys.BlackRosterService;
import com.eghm.utils.IpUtil;
import com.eghm.utils.WebUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author 二哥很猛
 * @since 2019/11/21 13:55
 */
@Slf4j
@AllArgsConstructor
public class IpBlackListFilter implements Filter {

    private final BlackRosterService blackRosterService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String ipAddress = IpUtil.getIpAddress(servletRequest);
        if (blackRosterService.isInterceptIp(ipAddress)) {
            log.warn("ip在黑名单中,禁止访问 [{}]", ipAddress);
            WebUtil.printJson(servletResponse, ErrorCode.FORBIDDEN_ACCESS);
        } else {
            chain.doFilter(request, response);
        }
    }

}
