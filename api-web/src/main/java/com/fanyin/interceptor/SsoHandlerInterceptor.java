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
        //是否开启单客户端登陆,即一个账号只能在一个移动设备上登陆
        if(systemConfigApi.getBoolean(ConfigConstant.SINGLE_CLIENT_LOGIN)){
            RequestMessage message = RequestThreadLocal.get();
            //大于0证明用户已经在前一步登录认证成功
            //等于0且能走到该拦截器证明访问的接口不需要登陆,且用户确实没有登陆,不需要判断单设备登陆
            if(message.getUserId() > 0){
                AccessToken accessToken = accessTokenService.getByUserId(message.getUserId());
                if(accessToken == null){
                    throw new BusinessException(ErrorCode.ACCESS_TOKEN_TIMEOUT);
                }
                if(!accessToken.getAccessKey().equals(message.getAccessKey())){
                    throw new BusinessException(ErrorCode.MULTIPLE_CLIENT_LOGIN);
                }
            }
        }
        return true;
    }

}
