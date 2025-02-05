package com.eghm.web.configuration.filter;


import cn.hutool.core.io.IoUtil;
import cn.hutool.http.Header;
import com.eghm.configuration.AbstractIgnoreFilter;
import com.eghm.constants.CommonConstant;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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

    @Slf4j
    public static class ByteHttpServletRequestWrapper extends HttpServletRequestWrapper {

        private byte[] body;

        ByteHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream(), CommonConstant.CHARSET));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            if (super.getHeader(Header.CONTENT_TYPE.getValue()) == null || super.getHeader(Header.CONTENT_TYPE.getValue()).startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                return super.getInputStream();
            } else {
                if (this.body == null) {
                    this.body = this.filterBody(IoUtil.read(super.getInputStream(), StandardCharsets.UTF_8)).getBytes();
                }
                final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body);
                return new ServletInputStream() {

                    @Override
                    public boolean isFinished() {
                        return false;
                    }

                    @Override
                    public boolean isReady() {
                        return true;
                    }

                    @Override
                    public void setReadListener(ReadListener listener) {
                        log.warn("接受到请求啦, ReadListener不为空");
                    }

                    @Override
                    public int read() {
                        return byteArrayInputStream.read();
                    }
                };
            }
        }

        /**
         * 过滤内容
         *
         * @param content content
         * @return 默认不处理
         */
        protected @NonNull String filterBody(String content) {
            return content;
        }
    }
}
