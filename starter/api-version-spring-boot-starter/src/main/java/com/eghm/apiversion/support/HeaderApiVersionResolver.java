package com.eghm.apiversion.support;

import com.eghm.apiversion.config.ApiVersionProperties;
import com.eghm.apiversion.spi.ApiVersionResolver;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 请求头版本号解析器默认实现
 */
public class HeaderApiVersionResolver implements ApiVersionResolver {

    private final ApiVersionProperties properties;

    public HeaderApiVersionResolver(ApiVersionProperties properties) {
        this.properties = properties;
    }

    /**
     * 从指定请求头读取客户端版本。
     *
     * @param request 当前 HTTP 请求
     * @return 请求头中的版本值；请求头不存在时返回 {@code null}
     */
    @Override
    public String resolve(HttpServletRequest request) {
        return request.getHeader(properties.getHeaderName());
    }
}
