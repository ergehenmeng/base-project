package com.eghm.web.configuration.interceptor;

import com.eghm.common.constant.AppHeader;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.DataException;
import com.eghm.common.exception.ParameterException;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.RequestMessage;
import com.eghm.model.dto.ext.RedisToken;
import com.eghm.model.vo.user.LoginDeviceVO;
import com.eghm.service.common.TokenService;
import com.eghm.service.user.LoginLogService;
import com.eghm.web.annotation.SkipAccess;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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

    private final LoginLogService loginLogService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if (!supportHandler(handler)) {
            return true;
        }
        boolean skipAccess = this.skipAccess(handler);
        RequestMessage message = ApiHolder.get();
        this.tryLoginVerify(request.getHeader(AppHeader.TOKEN), request.getHeader(AppHeader.REFRESH_TOKEN), message, !skipAccess);
        return true;
    }


    /**
     * 尝试获取登陆用户的信息(前提用户确实已经登陆,如果没有登陆则获取不到)
     *
     * @param accessToken 登陆的token
     * @param refreshToken 刷新的token
     * @param message 用户信息
     * @param exception true:获取用户信息失败是否要抛异常 true:抛异常 false:不做任何处理
     */
    private void tryLoginVerify(@Nullable String accessToken, @Nullable String refreshToken, RequestMessage message, boolean exception) {
        boolean shouldSkip = (accessToken == null || refreshToken == null) && !exception;
        if (shouldSkip) {
            return;
        }
        if (accessToken == null || refreshToken == null) {
            throw new ParameterException(ErrorCode.ACCESS_TOKEN_NULL);
        }

        // 尝试获取用户信息
        RedisToken redisToken = tokenService.getByAccessToken(accessToken);
        if (redisToken != null) {
            message.setUserId(redisToken.getUserId());
        }

        // token获取失败,尝试从refreshToken中获取用户信息
        if (message.getUserId() == null) {
            redisToken = tokenService.getByRefreshToken(refreshToken);
            if (redisToken != null) {
                // 在accessToken过期时,可通过refreshToken进行刷新用户信息
                log.info("用户token已失效, 采用refreshToken重新激活 userId:[{}]", redisToken.getUserId());
                tokenService.cacheToken(redisToken);
                message.setUserId(redisToken.getUserId());
            }
        }
        // 接口确实需要登陆但是也确实没有获取到userId,则抛异常
        if (exception && message.getUserId() == null) {
            RedisToken offlineRedisToken = tokenService.getOfflineToken(accessToken);
            if (offlineRedisToken != null) {
                log.warn("用户其他设备登陆,accessToken:[{}],userId:[{}]", accessToken, offlineRedisToken.getUserId());
                tokenService.cleanOfflineToken(accessToken);
                // 异常接口捎带一些额外信息方便移动端提醒用户
                throw this.createOfflineException(offlineRedisToken.getUserId());
            }
            log.warn("令牌为空,accessToken:[{}],refreshToken:[{}]", accessToken, refreshToken);
            throw new ParameterException(ErrorCode.ACCESS_TOKEN_TIMEOUT);
        }

    }

    /**
     * 给予用户被其他设备登陆的提示信息
     * @param userId userId
     * @return exception
     */
    private DataException createOfflineException(Long userId) {
        LoginDeviceVO vo = loginLogService.getLastLogin(userId);
        return new DataException(ErrorCode.KICK_OFF_LINE, vo);
    }



    /**
     * 是否需要登陆校验
     *
     * @param handler handlerMethod
     * @return true:需要验签 false不需要
     */
    private boolean skipAccess(Object handler) {
        return this.getAnnotation(handler, SkipAccess.class) != null;
    }

}
