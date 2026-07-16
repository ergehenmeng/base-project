package com.eghm.apiversion.spi;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 从 HTTP 请求中解析客户端 API 版本的扩展点。
 */
@FunctionalInterface
public interface ApiVersionResolver {

    /**
     * 解析当前客户端请求的 API 版本。
     *
     * @param request 当前 HTTP 请求
     * @return 客户端版本；请求未携带版本时可返回 {@code null} 或空字符串
     */
    String resolve(HttpServletRequest request);
}
