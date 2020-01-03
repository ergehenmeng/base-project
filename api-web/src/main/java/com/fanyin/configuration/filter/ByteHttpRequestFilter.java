package com.fanyin.configuration.filter;

import com.fanyin.configuration.AbstractIgnoreFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求参数重复使用
 *
 * @author 二哥很猛
 * @date 2018/8/28 16:38
 */
public class ByteHttpRequestFilter extends AbstractIgnoreFilter {

    @Override
    protected void doInternalFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //需要包装字节对象方便多次读写
        ServletRequestWrapper wrapper = new ByteHttpServletRequestWrapper((HttpServletRequest) request);
        chain.doFilter(wrapper, response);
    }
}
