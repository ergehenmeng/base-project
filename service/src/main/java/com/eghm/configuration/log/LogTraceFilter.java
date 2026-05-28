package com.eghm.configuration.log;

import com.eghm.utils.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * 日志链路追踪过滤器
 *
 * @author 二哥很猛
 * @since 2023/3/20
 */
public class LogTraceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            LogTraceHolder.putTraceId(StringUtil.randomHex(16));
            filterChain.doFilter(request, response);
        } finally {
            LogTraceHolder.clear();
        }
    }
}
