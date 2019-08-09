package com.fanyin.configuration.security;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 附加信息管理 例如验证码 手机短信验证等
 * @author 二哥很猛
 * @date 2018/1/25 14:46
 */
public class CustomAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new ImageCodeAuthenticationDetails(context);
    }
}
