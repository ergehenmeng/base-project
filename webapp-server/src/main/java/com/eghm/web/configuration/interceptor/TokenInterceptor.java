package com.eghm.web.configuration.interceptor;

import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.constant.AppHeader;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RedisToken;
import com.eghm.dto.ext.RequestMessage;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.DataException;
import com.eghm.exception.ParameterException;
import com.eghm.service.common.TokenService;
import com.eghm.service.member.LoginService;
import com.eghm.vo.member.LoginDeviceVO;
import com.eghm.web.annotation.AccessToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆令牌验证,主要用于app,pc等前后端分离
 * 默认针对全部接口进行登陆校验校验成功后将用户信息保存到线程变量中
 * 如果添加@SkipAccess则跳过登陆校验,但如果传递过来有相关用户登陆信息
 * 依旧将会将用户信息存入到线程变量中
 *
 * @author 二哥很猛
 * @date 2018/1/23 12:02
 */
@Slf4j
@AllArgsConstructor
public class TokenInterceptor implements InterceptorAdapter {

    private final TokenService tokenService;

    private final LoginService loginService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        RequestMessage message = ApiHolder.get();
        String token = request.getHeader(AppHeader.TOKEN);
        // 从token中获取用户信息, 如果获取不到,则从refreshToken中获取用户信息, 如果还是获取不到,则根据@AccessToken来决定是否抛异常
        if (token != null) {
            RedisToken redisToken = tokenService.getByAccessToken(token);
            if (redisToken != null) {
                message.setMemberId(redisToken.getMemberId());
            }
            String refreshToken = request.getHeader(AppHeader.REFRESH_TOKEN);
            if (message.getMemberId() == null && refreshToken != null) {
                redisToken = tokenService.getByRefreshToken(refreshToken);
                if (redisToken != null) {
                    // 在accessToken过期时,可通过refreshToken进行刷新用户信息
                    log.info("用户token已失效, 采用refreshToken重新激活 memberId:[{}]", redisToken.getMemberId());
                    tokenService.cacheToken(redisToken);
                    message.setMemberId(redisToken.getMemberId());
                }
            }
            this.accessTokenCheck(message.getMemberId(), token, handler);
        }
        return true;
    }

    /**
     * 校验用户是否需要强制登陆, 如果需要强制登陆,则必须包含memberId
     * @param memberId memberId, 可能为空
     * @param token token
     * @param handler handler
     */
    private void accessTokenCheck(@Nullable Long memberId, String token, Object handler) {
        // 如果用户需要登陆,且用户未获取到,则抛异常
        if (memberId == null && this.hasAccessToken(handler)) {
            RedisToken redisToken = tokenService.getOfflineToken(token);

            if (redisToken != null) {
                log.warn("用户其他设备登陆,accessToken:[{}],memberId:[{}]", token, redisToken.getMemberId());
                tokenService.cleanOfflineToken(token);
                // 异常接口捎带一些额外信息方便移动端提醒用户
                throw this.createOfflineException(redisToken.getMemberId());
            }
            log.warn("令牌为空,accessToken:[{}]", token);
            throw new ParameterException(ErrorCode.ACCESS_TOKEN_TIMEOUT);
        }
    }

    /**
     * 给予用户被其他设备登陆的提示信息
     * @param memberId memberId
     * @return exception
     */
    private DataException createOfflineException(Long memberId) {
        LoginDeviceVO vo = loginService.getLastLogin(memberId);
        return new DataException(ErrorCode.KICK_OFF_LINE, vo);
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
