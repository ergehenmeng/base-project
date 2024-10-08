package com.eghm.web.configuration.interceptor;

import com.eghm.cache.CacheService;
import com.eghm.annotation.SkipPerm;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.dto.ext.UserToken;
import com.eghm.enums.ErrorCode;
import com.eghm.utils.WebUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public boolean beforeHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        if (this.getAnnotation(handler, SkipPerm.class) != null || this.getClassAnnotation(handler, SkipPerm.class) != null) {
            return true;
        }
        UserToken user = SecurityHolder.getUser();
        if (user != null) {
            String value = cacheService.getValue(CacheConstant.LOCK_SCREEN + user.getId());
            if (value != null) {
                WebUtil.printJson(response, ErrorCode.LOCK_SCREEN);
                return false;
            }
        }
        return true;
    }

}
