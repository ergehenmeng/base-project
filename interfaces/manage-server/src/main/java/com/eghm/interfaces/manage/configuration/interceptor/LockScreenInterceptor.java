package com.eghm.interfaces.manage.configuration.interceptor;

import com.eghm.annotation.SkipPerm;
import com.eghm.cache.CacheService;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.configuration.authentication.SecurityHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.dto.ext.UserToken;
import com.eghm.enums.ErrorCode;
import com.eghm.utils.WebUtil;
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
