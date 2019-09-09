package com.fanyin.interceptor;

import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.service.cache.CacheProxyService;
import com.fanyin.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 二哥很猛
 * @date 2019/9/9 14:09
 */
public class IpHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CacheProxyService cacheProxyService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ipAddress = IpUtil.getIpAddress(request);
        if(cacheProxyService.isInterceptIp(ipAddress)){
            throw new BusinessException(ErrorCodeEnum.SYSTEM_AUTH);
        }
        return true;
    }
}
