package com.eghm.web.configuration.filter;

import com.eghm.enums.ErrorCode;
import com.eghm.configuration.AbstractIgnoreFilter;
import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.ext.UserToken;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.common.AccessTokenService;
import com.eghm.utils.WebUtil;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author 二哥很猛
 * @since 2022/11/4
 */
@AllArgsConstructor
public class AuthFilter extends AbstractIgnoreFilter {

    private final SystemProperties.ManageProperties manageProperties;

    private final AccessTokenService accessTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(manageProperties.getToken().getHeader());
        String prefix = manageProperties.getToken().getPrefix();
        if (header != null && header.startsWith(prefix)) {
            Optional<UserToken> optional = accessTokenService.parseToken(header.replace(prefix, ""));
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
