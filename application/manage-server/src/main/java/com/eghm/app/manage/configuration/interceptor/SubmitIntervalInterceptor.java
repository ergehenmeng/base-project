package com.eghm.app.manage.configuration.interceptor;

import com.eghm.foundation.cache.service.CacheService;
import com.eghm.foundation.core.configuration.authentication.SecurityHolder;
import com.eghm.foundation.core.constants.CacheConstant;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.web.config.interceptor.InterceptorAdapter;
import com.eghm.foundation.web.utility.IpUtil;
import com.eghm.foundation.web.utility.WebUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author 殿小二
 * @since 2020/12/15
 */
@AllArgsConstructor
public class SubmitIntervalInterceptor implements InterceptorAdapter {
    
    private final CacheService cacheService;

    @Override
    public boolean beforeHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws IOException {
        // 只针对post请求有效
        if (!HttpMethod.POST.matches(request.getMethod())) {
            return true;
        }
        Long userId = SecurityHolder.tryGetUserId();
        String uri = request.getRequestURI();
        String key;
        // 如果用户未登录则以ip作为过滤维度,否则以用户作为维度
        if (userId == null) {
            key = String.format(CacheConstant.SUBMIT_LIMIT, IpUtil.getIpAddress(request), uri);
        } else {
            key = String.format(CacheConstant.SUBMIT_LIMIT, userId, uri);
        }
        if (!cacheService.putIfAbsent(key, CacheConstant.PLACE_HOLDER, 1000, TimeUnit.MILLISECONDS)) {
            WebUtil.printJson(response, ErrorCode.SUBMIT_FREQUENTLY);
            return false;
        }
        return true;
    }


}
