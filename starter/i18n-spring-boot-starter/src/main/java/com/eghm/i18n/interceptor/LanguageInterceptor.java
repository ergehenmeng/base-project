package com.eghm.i18n.interceptor;

import com.eghm.i18n.config.I18nProperties;
import com.eghm.i18n.context.LanguageContextHolder;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Locale;

/**
 * 国际化拦截器
 * @author wyb-eghm
 * @since 2026/5/15
 */
@RequiredArgsConstructor
public class LanguageInterceptor implements HandlerInterceptor {

    private final I18nProperties i18nProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nullable Object handler) {
        String language = request.getHeader(i18nProperties.getHeaderName());
        if (!StringUtils.hasText(language)) {
            language = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        }
        if (StringUtils.hasText(language)) {
            Locale locale = Locale.forLanguageTag(language.split(",")[0].trim());
            LanguageContextHolder.setLocale(locale);
        }
        return true;
    }

    @Override
    public void afterCompletion(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nullable Object handler, Exception ex) {
        LanguageContextHolder.clear();
    }

}
