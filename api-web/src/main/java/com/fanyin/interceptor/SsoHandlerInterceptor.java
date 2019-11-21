package com.fanyin.interceptor;

import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.constants.ConfigConstant;
import com.fanyin.model.ext.AccessToken;
import com.fanyin.model.ext.RequestMessage;
import com.fanyin.model.ext.RequestThreadLocal;
import com.fanyin.service.common.AccessTokenService;
import com.fanyin.service.system.impl.SystemConfigApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 单客户单登陆
 * @author 二哥很猛
 * @date 2019/8/13 13:54
 */
public class SsoHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        //是否开启单客户端登陆,即在同一类设备上只允许一个账号登陆
        if(systemConfigApi.getBoolean(ConfigConstant.SINGLE_CLIENT_LOGIN)){
            RequestMessage message = RequestThreadLocal.get();
            AccessToken accessToken = accessTokenService.getByUserId(message.getUserId());
            if(accessToken == null){
                throw new BusinessException(ErrorCode.ACCESS_TOKEN_TIMEOUT);
            }
            if(!accessToken.getAccessKey().equals(message.getAccessKey())){
                throw new BusinessException(ErrorCode.MULTIPLE_CLIENT_LOGIN);
            }
        }
        return true;
    }

}
