package com.fanyin.configuration.security;

import com.fanyin.ext.RespBody;
import com.fanyin.utils.IpUtil;
import com.fanyin.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆校验通过的后置处理器
 * @author 二哥很猛
 * @date 2018/1/25 10:28
 */
@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        super.clearAuthenticationAttributes(request);
        SecurityOperator principal = (SecurityOperator)authentication.getPrincipal();
        log.debug("用户:[{}]登陆系统,登陆IP:[{}]",principal.getOperatorName(), IpUtil.getIpAddress(request));
        WebUtil.printJson(response, RespBody.getInstance());
    }
}
