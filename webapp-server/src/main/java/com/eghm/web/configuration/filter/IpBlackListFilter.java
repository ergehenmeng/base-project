package com.eghm.web.configuration.filter;

import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ErrorCode;
import com.eghm.service.sys.BlackRosterService;
import com.eghm.utils.IpUtil;
import com.eghm.utils.WebUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            WebUtil.printJson(servletResponse, RespBody.error(ErrorCode.SYSTEM_AUTH));
        } else {
            chain.doFilter(request, response);
        }
    }

}
