package com.eghm.web.configuration.filter;

import com.eghm.common.enums.ErrorCode;
import com.eghm.configuration.AbstractIgnoreFilter;
import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.model.dto.ext.JwtManage;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.common.JwtTokenService;
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

    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(manageProperties.getJwt().getHeader());
        String prefix = manageProperties.getJwt().getPrefix();
        if (header != null && header.startsWith(prefix)) {
            Optional<JwtManage> optional = jwtTokenService.parseToken(header.replace(prefix, ""));
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
        WebUtil.printJson(response, RespBody.error(ErrorCode.LOGIN_EXPIRE));
    }

}
