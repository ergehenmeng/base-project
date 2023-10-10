package com.eghm.web.configuration.filter;


import com.eghm.configuration.AbstractIgnoreFilter;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求参数重复使用
 *
 * @author 二哥很猛
 * @date 2018/8/28 16:38
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
