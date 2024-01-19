package com.eghm.web.configuration.interceptor;

import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.constant.AppHeader;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.MemberToken;
import com.eghm.dto.ext.RequestMessage;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.service.common.TokenService;
import com.eghm.service.member.LoginService;
import com.eghm.web.annotation.AccessToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆令牌验证,主要用于app,pc等前后端分离
 * 默认针对全部接口进行登陆校验校验成功后将用户信息保存到线程变量中
 * 如果添加@SkipAccess则跳过登陆校验,但如果传递过来有相关用户登陆信息
 * 依旧将会将用户信息存入到线程变量中
 *
 * @author 二哥很猛
 * @since 2018/1/23 12:02
 */
@Slf4j
@AllArgsConstructor
public class TokenInterceptor implements InterceptorAdapter {

    private final TokenService tokenService;

    private final LoginService loginService;

    @Override
    public boolean beforeHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        RequestMessage message = ApiHolder.get();
        String token = request.getHeader(AppHeader.TOKEN);
        // 从token中获取用户信息, 如果获取不到,则从refreshToken中获取用户信息, 如果还是获取不到,则根据@AccessToken来决定是否抛异常
        if (token != null) {
            this.setMemberId(message, token, request);
            this.accessTokenCheck(message, token, handler);
        }
        return true;
    }

    /**
     * 根据token或refreshToken查询会员id,并设置到message中
     *
     * @param message 用户信息
     * @param token   token
     * @param request request
     */
    private void setMemberId(RequestMessage message, String token, HttpServletRequest request) {
        MemberToken memberToken = tokenService.getByAccessToken(token);
        if (memberToken != null) {
            message.setMemberId(memberToken.getMemberId());
        }

        String refreshToken = request.getHeader(AppHeader.REFRESH_TOKEN);
        if (message.getMemberId() == null && refreshToken != null) {
            memberToken = tokenService.getByRefreshToken(refreshToken);
            if (memberToken != null) {
                message.setMemberId(memberToken.getMemberId());
                tokenService.cacheToken(memberToken);
                log.info("用户token已失效, 采用refreshToken重新激活 memberId:[{}]", memberToken.getMemberId());
            } else {
                log.warn("用户token与refreshToken均已失效 [{}] [{}]", token, refreshToken);
            }
        }
    }

    /**
     * 校验用户是否需要强制登陆, 如果需要强制登陆,则必须包含memberId
     *
     * @param message 用户信息
     * @param token   token
     * @param handler handler
     */
    private void accessTokenCheck(RequestMessage message, String token, Object handler) {
        if (message.getMemberId() == null && this.hasAccessToken(handler)) {
            // 如果用户需要登陆,且用户未获取到,则抛异常
            log.warn("用户登录已失效,请重新登陆 token:[{}]", token);
            throw new BusinessException(ErrorCode.LOGIN_TIMEOUT);
        }
    }

    /**
     * 是否需要登陆校验
     *
     * @param handler handlerMethod
     * @return true:需要验签 false不需要
     */
    private boolean hasAccessToken(Object handler) {
        return this.getClassAnnotation(handler, AccessToken.class) != null || this.getAnnotation(handler, AccessToken.class) != null;
    }

}
