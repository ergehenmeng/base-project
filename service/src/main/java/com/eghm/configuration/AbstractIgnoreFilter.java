package com.eghm.configuration;

import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/7/4 13:48
 */
public abstract class AbstractIgnoreFilter extends OncePerRequestFilter {

    /**
     * 排除忽略的地址或者模糊匹配的url
     */
    private static final List<String> EXCLUDE = Lists.newArrayListWithCapacity(4);

    /**
     * 匹配器
     */
    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return EXCLUDE.stream().anyMatch(url -> MATCHER.match(url, request.getRequestURI()));
    }

    /**
     * 排除不需要拦截的地址
     *
     * @param matchUrl 不需要拦截的地址
     */
    public void exclude(@NonNull String... matchUrl) {
        EXCLUDE.addAll(Lists.newArrayList(matchUrl));
    }

}
