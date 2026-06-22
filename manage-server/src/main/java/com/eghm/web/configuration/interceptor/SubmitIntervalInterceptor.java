package com.eghm.web.configuration.interceptor;

import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.configuration.authentication.SecurityHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.utils.IpUtil;
import com.eghm.utils.WebUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;

import java.io.IOException;

import static com.eghm.utils.CacheUtil.INTERVAL_CACHE;

/**
 * @author 殿小二
 * @since 2020/12/15
 */
@AllArgsConstructor
public class SubmitIntervalInterceptor implements InterceptorAdapter {

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
        if (INTERVAL_CACHE.asMap().putIfAbsent(key, true) != null) {
            WebUtil.printJson(response, ErrorCode.SUBMIT_FREQUENTLY);
            return false;
        }
        return true;
    }


}
