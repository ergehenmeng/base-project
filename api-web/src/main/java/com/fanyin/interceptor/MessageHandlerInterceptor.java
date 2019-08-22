package com.fanyin.interceptor;

import com.fanyin.common.constant.HeaderConstant;
import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.common.exception.RequestException;
import com.fanyin.model.ext.RequestMessage;
import com.fanyin.model.ext.RequestThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基础头信息收集
 * @author 二哥很猛
 * @date 2019/7/4 14:24
 */
public class MessageHandlerInterceptor extends HandlerInterceptorAdapter {

    /**
     * 请求头最大长度 默认256
     */
    private static final int MAX_HEADER_LENGTH = 256;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //app请求头信息
        String channel = request.getHeader(HeaderConstant.CHANNEL);
        String version = request.getHeader(HeaderConstant.VERSION);
        String osVersion = request.getHeader(HeaderConstant.OS_VERSION);

        if(checkHeaderLength(channel)
                || checkHeaderLength(version)
                || checkHeaderLength(osVersion)){
            //该信息会保存在Thread中,会占用一定内存,防止恶意攻击做此判断
            throw new RequestException(ErrorCodeEnum.REQUEST_PARAM_ILLEGAL);
        }
        RequestMessage message = RequestMessage.builder().version(version).channel(channel).osVersion(osVersion).build();
        RequestThreadLocal.set(message);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestThreadLocal.remove();
    }

    /**
     * 检查header请求参数是否过长,防止恶意攻击导致服务器挂掉,最大长度不能超过256
     * @param headerValue 字符串
     * @return true不合法 false合法
     */
    private boolean checkHeaderLength(String headerValue){
        return headerValue != null && headerValue.length() > MAX_HEADER_LENGTH;
    }

}
