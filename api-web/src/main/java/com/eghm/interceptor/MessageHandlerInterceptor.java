package com.eghm.interceptor;

import com.eghm.common.constant.AppHeader;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.RequestException;
import com.eghm.model.ext.RequestMessage;
import com.eghm.model.ext.RequestThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基础头信息收集 将定义好的头信息参数全部放入RequestMessage对象中
 *
 * @author 二哥很猛
 * @date 2019/7/4 14:24
 */
public class MessageHandlerInterceptor extends HandlerInterceptorAdapter {

    /**
     * 请求头最大长度 默认256
     */
    private static final int MAX_HEADER_LENGTH = 256;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //app请求头信息
        String channel = request.getHeader(AppHeader.CHANNEL);
        String version = request.getHeader(AppHeader.VERSION);
        String osVersion = request.getHeader(AppHeader.OS_VERSION);
        String deviceBrand = request.getHeader(AppHeader.DEVICE_BRAND);
        String deviceModel = request.getHeader(AppHeader.DEVICE_MODEL);
        if (checkHeaderLength(channel)
                || checkHeaderLength(version)
                || checkHeaderLength(osVersion)
                || checkHeaderLength(deviceBrand)
                || checkHeaderLength(deviceModel)) {
            // 该信息会保存在Thread中,会占用一定内存,防止恶意攻击做此判断
            throw new RequestException(ErrorCode.REQUEST_PARAM_ILLEGAL);
        }
        RequestMessage message = RequestThreadLocal.get();
        message.setVersion(version);
        message.setChannel(channel);
        message.setOsVersion(osVersion);
        message.setDeviceBrand(deviceBrand);
        message.setDeviceModel(deviceModel);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RequestThreadLocal.remove();
    }

    /**
     * 检查header请求参数是否过长,防止恶意攻击导致服务器挂掉,最大长度不能超过256
     *
     * @param headerValue 字符串
     * @return true不合法 false合法
     */
    private boolean checkHeaderLength(String headerValue) {
        return headerValue != null && headerValue.length() > MAX_HEADER_LENGTH;
    }

}
