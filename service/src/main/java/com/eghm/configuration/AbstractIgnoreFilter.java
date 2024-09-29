package com.eghm.configuration;

import com.google.common.collect.Lists;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/7/4 13:48
 */
public abstract class AbstractIgnoreFilter extends OncePerRequestFilter {

    /**
     * 排除忽略的地址或者模糊匹配的url
     */
    private final List<String> exclude = Lists.newArrayListWithCapacity(4);

    /**
     * 匹配器
     */
    private final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return this.anyMatch(exclude, request);
    }

    /**
     * 排除不需要拦截的地址
     *
     * @param matchUrl 不需要拦截的地址
     */
    public void exclude(@NonNull String... matchUrl) {
        exclude.addAll(Lists.newArrayList(matchUrl));
    }

    /**
     * 根据给定的url列表, 确认请求url是否在指定url列表中
     *
     * @param urlList url列表
     * @param request 请求request
     * @return boolean  true:在urlList, false:不在
     */
    protected boolean anyMatch(List<String> urlList, HttpServletRequest request) {
        return urlList.stream().anyMatch(url -> matcher.match(url, request.getRequestURI()));
    }

}
