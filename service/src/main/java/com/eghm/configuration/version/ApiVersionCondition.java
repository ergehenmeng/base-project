package com.eghm.configuration.version;

import com.eghm.constants.AppHeader;
import com.eghm.utils.VersionUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import static com.eghm.constants.CommonConstant.DEFAULT_VERSION;


/**
 * API版本请求条件 用于根据请求头中的版本号匹配对应的Controller方法
 *
 * @param apiVersion 接口版本号
 * @since 2026/6/15
 * @author eghm
 */
public record ApiVersionCondition(String apiVersion) implements RequestCondition<ApiVersionCondition> {
    
    @Nonnull
    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        return new ApiVersionCondition(other.apiVersion());
    }
    
    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        String versionStr = request.getHeader(AppHeader.API_VERSION);
        if (versionStr == null || versionStr.trim().isEmpty()) {
            return new ApiVersionCondition(DEFAULT_VERSION);
        }
        try {
            boolean gte = VersionUtil.gte(versionStr.trim(), apiVersion);
            if (gte) {
                return this;
            }
        } catch (NumberFormatException e) {
            return new ApiVersionCondition(DEFAULT_VERSION);
        }
        return null;
    }
    
    @Override
    public int compareTo(ApiVersionCondition other, @Nonnull HttpServletRequest request) {
        return VersionUtil.gte(this.apiVersion(), other.apiVersion()) ? 0 : 1;
    }
}
