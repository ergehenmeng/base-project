package com.eghm.configuration.filter;

import com.eghm.common.enums.ErrorCode;
import com.eghm.model.ext.RespBody;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.utils.IpUtil;
import com.eghm.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 二哥很猛
 * @date 2019/11/21 13:55
 */
public class IpBlackListFilter implements Filter {

    @Autowired
    private CacheProxyService cacheProxyService;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String ipAddress = IpUtil.getIpAddress(servletRequest);
        if (cacheProxyService.isInterceptIp(ipAddress)) {
            WebUtil.printJson(servletResponse, RespBody.error(ErrorCode.SYSTEM_AUTH));
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
