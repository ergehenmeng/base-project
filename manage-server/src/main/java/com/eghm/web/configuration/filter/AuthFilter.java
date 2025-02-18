package com.eghm.web.configuration.filter;

import com.eghm.common.UserTokenService;
import com.eghm.configuration.AbstractIgnoreFilter;
import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.ext.UserToken;
import com.eghm.enums.ErrorCode;
import com.eghm.utils.WebUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Optional;

/**
 * @author 二哥很猛
 * @since 2022/11/4
 */
@AllArgsConstructor
public class AuthFilter extends AbstractIgnoreFilter {

    private final UserTokenService userTokenService;

    private final SystemProperties.ManageProperties manageProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(manageProperties.getToken().getTokenName());
        String prefix = manageProperties.getToken().getTokenPrefix();
        if (header != null && header.startsWith(prefix)) {
            Optional<UserToken> optional = userTokenService.parseToken(header.replace(prefix, ""));
            if (optional.isPresent()) {
                try {
                    SecurityHolder.setToken(optional.get());
                    filterChain.doFilter(request, response);
                } finally {
                    SecurityHolder.remove();
                }
                return;
            }
        }
        WebUtil.printJson(response, RespBody.error(ErrorCode.LOGIN_TIMEOUT));
    }

}
