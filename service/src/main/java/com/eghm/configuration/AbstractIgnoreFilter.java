package com.eghm.configuration;

import com.google.common.collect.Lists;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/7/4 13:48
 */
public abstract class AbstractIgnoreFilter extends OncePerRequestFilter {

    /**
     * 排除忽略的地址或者模糊匹配的url
     */
    private final List<String> exclude = Lists.newArrayListWithCapacity(4);

    private final AntPathMatcher matcher = new AntPathMatcher();

    /**
     * 排除不需要拦截的地址
     *
     * @param matchUrl 不需要拦截的地址
     */
    public void exclude(@NonNull String... matchUrl) {
        exclude.addAll(Lists.newArrayList(matchUrl));
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        for (String url : exclude) {
            if (matcher.match(url, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }

}
