package com.eghm.configuration.log;

import com.eghm.common.constant.CommonConstant;
import com.eghm.common.utils.StringUtil;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author 二哥很猛
 * @since 2023/3/20
 */
public class LogTraceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String traceId = StringUtil.randomLowerCase(16);
            MDC.put(CommonConstant.TRACE_ID, traceId);
            LogTraceHolder.put(traceId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
            LogTraceHolder.clear();
        }
    }
}
