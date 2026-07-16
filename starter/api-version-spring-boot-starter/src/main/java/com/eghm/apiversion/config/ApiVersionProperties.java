package com.eghm.apiversion.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * API 版本路由配置属性。
 */
@ConfigurationProperties(prefix = "application.api-version")
public class ApiVersionProperties {
    
    /**
     * 是否启用 API 版本路由。
     */
    private boolean enabled = true;
    
    /**
     * 默认版本解析器读取的请求头名称。
     */
    private String headerName = "Version";
    
    /**
     * 标识当前接口版本已废弃的响应头名称。
     */
    private String deprecatedHeaderName = "Api-Deprecated";
    
    /**
     * 返回接口废弃提示消息的响应头名称。
     */
    private String deprecatedMessageHeaderName = "Api-Deprecated-Message";
    
    /**
     * 拦截器包含的 Spring MVC 路径模式，默认拦截全部请求。
     */
    private List<String> pathPatterns = new ArrayList<>(List.of("/**"));
    
    /**
     * 拦截器排除的 Spring MVC 路径模式。
     */
    private List<String> excludePathPatterns = new ArrayList<>();
    
    /**
     * 拦截器执行顺序；数值越小，执行优先级越高。
     */
    private int order = Integer.MIN_VALUE;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getDeprecatedHeaderName() {
        return deprecatedHeaderName;
    }

    public void setDeprecatedHeaderName(String deprecatedHeaderName) {
        this.deprecatedHeaderName = deprecatedHeaderName;
    }
    
    public String getDeprecatedMessageHeaderName() {
        return deprecatedMessageHeaderName;
    }
    
    public void setDeprecatedMessageHeaderName(String deprecatedMessageHeaderName) {
        this.deprecatedMessageHeaderName = deprecatedMessageHeaderName;
    }
    
    public List<String> getPathPatterns() {
        return pathPatterns;
    }
    
    public void setPathPatterns(List<String> pathPatterns) {
        this.pathPatterns = pathPatterns;
    }
    
    public List<String> getExcludePathPatterns() {
        return excludePathPatterns;
    }

    public void setExcludePathPatterns(List<String> excludePathPatterns) {
        this.excludePathPatterns = excludePathPatterns;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
