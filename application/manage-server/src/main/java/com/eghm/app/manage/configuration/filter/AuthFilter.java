package com.eghm.app.manage.configuration.filter;

import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.configuration.authentication.SecurityHolder;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.security.UserToken;
import com.eghm.foundation.web.utility.WebUtil;
import com.eghm.platform.iam.service.UserTokenService;
import com.google.common.collect.Lists;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.eghm.foundation.core.constants.ApplicationHeader.TOKEN;

/**
 * 针对后端转发的接口,依旧要经过该过滤器来获取用户信息, 故该接口不实现OncePerRequestFilter
 *
 * @author 二哥很猛
 * @since 2022/11/4
 */
@AllArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final UserTokenService userTokenService;

    private final ApplicationProperties.ManageProperties manageProperties;

    private final AntPathMatcher matcher = new AntPathMatcher();

    private final List<String> exclude = Lists.newArrayListWithCapacity(4);
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(TOKEN);
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
        WebUtil.printJson(response, ErrorCode.LOGIN_TIMEOUT);
    }
    
    /**
     * 排除不需要拦截的地址
     *
     * @param matchUrl 不需要拦截的地址
     */
    public void exclude(@Nonnull String... matchUrl) {
        exclude.addAll(Lists.newArrayList(matchUrl));
    }
    
    @Override
    protected boolean shouldNotFilter(@Nonnull HttpServletRequest request) {
        return exclude.stream().anyMatch(url -> matcher.match(url, request.getRequestURI()));
    }
}
