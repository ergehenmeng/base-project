package com.fanyin.configuration.security;

import com.fanyin.ext.RespBody;
import com.fanyin.utils.WebUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登陆
 * @author 二哥很猛
 * @date 2018/11/26 18:57
 */

public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        WebUtil.printJson(response, RespBody.getInstance());
    }
}
