package com.eghm.apiversion.condition;

import com.eghm.apiversion.spi.ApiVersionComparator;
import com.eghm.apiversion.spi.ApiVersionResolver;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

/**
 * 根据客户端版本匹配 Controller 处理方法的 Spring MVC 请求条件。
 *
 * <p>客户端版本大于或等于接口声明版本时视为匹配。同一路径存在多个匹配版本时，
 * 版本更高的接口实现优先。客户端未提供版本或版本格式非法时，当前条件不匹配。</p>
 *
 * @param apiVersion        接口声明的起始版本
 * @param versionResolver   客户端版本解析器
 * @param versionComparator 版本比较器
 * @since 2025/6/15
 */
public record ApiVersionCondition(String apiVersion, ApiVersionResolver versionResolver, ApiVersionComparator versionComparator) implements RequestCondition<ApiVersionCondition> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiVersionCondition.class);
    
    @Nonnull
    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        return new ApiVersionCondition(other.apiVersion(), versionResolver, versionComparator);
    }
    
    @Override
    public ApiVersionCondition getMatchingCondition(@Nonnull HttpServletRequest request) {
        String version = versionResolver.resolve(request);
        if (version == null || version.trim().isEmpty()) {
            return null;
        }
        try {
            if (versionComparator.greaterThanOrEqual(version.trim(), apiVersion)) {
                return this;
            }
        } catch (NumberFormatException e) {
            LOGGER.warn("版本号 {} 格式错误，无法匹配", version, e);
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionCondition other, @Nonnull HttpServletRequest request) {
        return versionComparator.compare(other.apiVersion(), apiVersion);
    }

}
