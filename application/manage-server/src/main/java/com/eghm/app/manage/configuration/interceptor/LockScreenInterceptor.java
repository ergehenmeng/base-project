package com.eghm.app.manage.configuration.interceptor;

import com.eghm.foundation.core.annotation.SkipPerm;
import com.eghm.foundation.cache.service.CacheService;
import com.eghm.foundation.web.config.interceptor.InterceptorAdapter;
import com.eghm.foundation.core.configuration.authentication.SecurityHolder;
import com.eghm.foundation.core.constants.CacheConstant;
import com.eghm.foundation.core.security.UserToken;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.web.utility.WebUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author 二哥很猛
 * @since 2022/11/4
 */
@Slf4j
@AllArgsConstructor
public class LockScreenInterceptor implements InterceptorAdapter {

    private final CacheService cacheService;

    @Override
    public boolean beforeHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws IOException {
        if (this.getAnnotation(handler, SkipPerm.class) != null) {
            return true;
        }
        UserToken user = SecurityHolder.getUser();
        if (user == null) {
            return true;
        }
        String value = cacheService.getValue(CacheConstant.LOCK_SCREEN + user.getId());
        if (value != null) {
            WebUtil.printJson(response, ErrorCode.LOCK_SCREEN);
            return false;
        }
        return true;
    }

}
