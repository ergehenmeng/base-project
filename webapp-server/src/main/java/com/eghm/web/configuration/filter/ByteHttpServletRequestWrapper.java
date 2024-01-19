package com.eghm.web.configuration.filter;

import cn.hutool.core.io.IoUtil;
import com.eghm.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author 二哥很猛
 * @since 2018/8/28 16:42
 */
@Slf4j
public class ByteHttpServletRequestWrapper extends HttpServletRequestWrapper {

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

        if (super.getHeader("Content-Type") == null) {
            return super.getInputStream();
        } else if (super.getHeader("Content-Type").startsWith("multipart/form-data")) {
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
    protected String filterBody(String content) {
        return content;
    }
}
