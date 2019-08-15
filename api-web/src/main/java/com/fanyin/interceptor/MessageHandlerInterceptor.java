package com.fanyin.interceptor;

import com.fanyin.common.constant.HeaderConstant;
import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.common.exception.RequestException;
import com.fanyin.model.ext.DataMessage;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基础头信息收集
 * @author 王艳兵
 * @date 2019/7/4 14:24
 */
public class MessageHandlerInterceptor extends HandlerInterceptorAdapter {

    /**
     * 存放用户信息
     */
    private static final ThreadLocal<DataMessage> TOKEN_LOCAL = new ThreadLocal<>();

    /**
     * 请求头最大长度 默认256
     */
    private static final int MAX_HEADER_LENGTH = 256;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //app请求头信息
        String requestType = request.getHeader(HeaderConstant.REQUEST_TYPE);
        String version = request.getHeader(HeaderConstant.VERSION);
        String osVersion = request.getHeader(HeaderConstant.OS_VERSION);

        if(checkHeaderLength(requestType)
                || checkHeaderLength(version)
                || checkHeaderLength(osVersion)){
            //该信息会保存在Thread中,会占用一定内存,防止恶意攻击做此判断
            throw new RequestException(ErrorCodeEnum.REQUEST_PARAM_ILLEGAL);
        }
        DataMessage message = new DataMessage(version,requestType,osVersion);
        TOKEN_LOCAL.set(message);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TOKEN_LOCAL.remove();
    }

    /**
     * 检查header请求参数是否过长,防止恶意攻击导致服务器挂掉,最大长度不能超过256
     * @param headerValue 字符串
     * @return true不合法 false合法
     */
    private boolean checkHeaderLength(String headerValue){
        return headerValue != null && headerValue.length() > MAX_HEADER_LENGTH;
    }

    /**
     * 获取登陆用户的基础信息
     * @return 基本信息 如果用户已登陆,则可以获取userId
     */
    public static DataMessage getMessage(){
        return TOKEN_LOCAL.get();
    }
}
