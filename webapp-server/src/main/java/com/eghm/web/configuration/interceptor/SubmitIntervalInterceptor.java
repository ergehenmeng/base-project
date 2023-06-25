package com.eghm.web.configuration.interceptor;

import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.constant.CacheConstant;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.enums.ErrorCode;
import com.eghm.service.cache.CacheService;
import com.eghm.utils.WebUtil;
import com.eghm.web.annotation.SubmitInterval;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 殿小二
 * @date 2020/12/15
 */
@AllArgsConstructor
public class SubmitIntervalInterceptor implements InterceptorAdapter {

    private final SystemProperties systemProperties;

    private final CacheService cacheService;

    @Override
    public boolean beforeHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        // 只针对post请求有效
        if (!HttpMethod.POST.matches(request.getMethod())) {
            return true;
        }
        Long memberId = ApiHolder.getMemberId();
        String uri = request.getRequestURI();
        String key = String.format(CacheConstant.SUBMIT_LIMIT, memberId, uri);
        if (cacheService.exist(key)) {
            WebUtil.printJson(response, ErrorCode.SUBMIT_FREQUENTLY);
            return false;
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
