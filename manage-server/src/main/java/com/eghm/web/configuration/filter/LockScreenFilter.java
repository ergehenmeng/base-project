package com.eghm.web.configuration.filter;

import com.eghm.configuration.AbstractIgnoreFilter;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CacheConstant;
import com.eghm.dto.ext.UserToken;
import com.eghm.enums.ErrorCode;
import com.eghm.cache.CacheService;
import com.eghm.utils.WebUtil;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wyb
 * @since 2023/6/12
 */
@AllArgsConstructor
public class LockScreenFilter extends AbstractIgnoreFilter {

    private final CacheService cacheService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        UserToken user = SecurityHolder.getUser();
        if (user != null) {
            String value = cacheService.getValue(CacheConstant.LOCK_SCREEN + user.getId());
            if (value != null) {
                WebUtil.printJson(response, ErrorCode.LOCK_SCREEN);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
