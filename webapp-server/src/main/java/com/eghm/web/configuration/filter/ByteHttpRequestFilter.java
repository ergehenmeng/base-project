package com.eghm.web.configuration.filter;


import com.eghm.configuration.AbstractIgnoreFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.IOException;

/**
 * 请求参数重复使用
 *
 * @author 二哥很猛
 * @since 2018/8/28 16:38
 */
@AllArgsConstructor
public class ByteHttpRequestFilter extends AbstractIgnoreFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 需要包装字节对象方便多次读写
        ServletRequestWrapper wrapper = new ByteHttpServletRequestWrapper(request);
        filterChain.doFilter(wrapper, response);
    }

}
