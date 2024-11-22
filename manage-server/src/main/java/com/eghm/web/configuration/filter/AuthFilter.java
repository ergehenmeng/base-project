package com.eghm.web.configuration.filter;

import com.eghm.common.UserTokenService;
import com.eghm.configuration.SystemProperties;
import com.eghm.dto.ext.SecurityHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.ext.UserToken;
import com.eghm.enums.ErrorCode;
import com.eghm.utils.WebUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * 针对后端转发的接口,依旧要经过该过滤器来获取用户信息, 故该接口不实现OncePerRequestFilter
 *
 * @author 二哥很猛
 * @since 2022/11/4
 */
@AllArgsConstructor
public class AuthFilter implements Filter {

    private final UserTokenService userTokenService;

    private final SystemProperties.ManageProperties manageProperties;

    private final AntPathMatcher matcher = new AntPathMatcher();

    private final List<String> exclude = Lists.newArrayListWithCapacity(4);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (this.shouldNotFilter(exclude, httpRequest)) {
            chain.doFilter(request, response);
        } else {
            String header = httpRequest.getHeader(manageProperties.getToken().getTokenName());
            String prefix = manageProperties.getToken().getTokenPrefix();
            if (header != null && header.startsWith(prefix)) {
                Optional<UserToken> optional = userTokenService.parseToken(header.replace(prefix, ""));
                if (optional.isPresent()) {
                    try {
                        SecurityHolder.setToken(optional.get());
                        chain.doFilter(request, response);
                    } finally {
                        SecurityHolder.remove();
                    }
                    return;
                }
            }
            WebUtil.printJson((HttpServletResponse) response, RespBody.error(ErrorCode.LOGIN_TIMEOUT));
        }
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
    private boolean shouldNotFilter(List<String> urlList, HttpServletRequest request) {
        return urlList.stream().anyMatch(url -> matcher.match(url, request.getRequestURI()));
    }
}
