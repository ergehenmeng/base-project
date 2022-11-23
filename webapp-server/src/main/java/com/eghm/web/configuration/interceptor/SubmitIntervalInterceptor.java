package com.eghm.web.configuration.interceptor;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.SystemProperties;
import com.eghm.web.annotation.SubmitInterval;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.service.cache.CacheService;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 殿小二
 * @date 2020/12/15
 */
@AllArgsConstructor
public class SubmitIntervalInterceptor implements InterceptorAdapter {

    private final SystemProperties systemProperties;

    private final CacheService cacheService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if (!this.supportHandler(handler)) {
            return true;
        }
        Long userId = ApiHolder.getUserId();
        String uri = request.getRequestURI();
        String key = String.format(CacheConstant.SUBMIT_LIMIT, userId, uri);
        if (cacheService.exist(key)) {
            throw new BusinessException(ErrorCode.SUBMIT_FREQUENTLY);
        }
        SubmitInterval annotation = this.getAnnotation(handler, SubmitInterval.class);
        if (annotation != null) {
            cacheService.setValue(key, true, annotation.value());
        } else {
            cacheService.setValue(key, true, systemProperties.getSubmitInterval());
        }
        return true;
    }
}
