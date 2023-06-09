package com.eghm.dto.ext;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;

/**
 * 针对移动端项目用于获取用户token信息的静态类
 * @author 二哥很猛
 * @date 2019/8/22 14:22
 */
public class ApiHolder {

    private static final TransmittableThreadLocal<RequestMessage> TOKEN_LOCAL = TransmittableThreadLocal.withInitial(RequestMessage::new);

    private ApiHolder() {
    }

    /**
     * 获取请求信息
     * @return 头信息及用户id
     */
    public static RequestMessage get(){
        return TOKEN_LOCAL.get();
    }

    /**
     * 清除线程对象信息
     */
    public static void remove(){
        TOKEN_LOCAL.remove();
    }

    /**
     * 前后端分离方式:获取访问来源
     * @return android ios h5 pc
     */
    public static String getChannel(){
        return get().getChannel();
    }

    /**
     * 强制获取用户信息
     */
    public static Long getMemberId() {
        return getMemberId(true);
    }

    /**
     * 尝试获取用户信息
     * @return 如果用户未登陆获取结果为null
     */
    public static Long tryGetMemberId() {
        return getMemberId(false);
    }

    /**
     * 获取用户id
     * @param needLogin 用户是否需要登陆,如果必须登陆登陆,用户id不存在则抛异常
     * @return 用户id
     */
    private static Long getMemberId(boolean needLogin) {
        Long memberId = get().getMemberId();
        if (needLogin && memberId == null) {
            throw new BusinessException(ErrorCode.MEMBER_LOGIN_TIMEOUT);
        }
        return memberId;
    }

    /**
     * 前后端分离方式:获取软件版本号 针对android和ios
     * @return v1.0.0
     */
    public static String getVersion(){
        return get().getVersion();
    }

    /**
     * 前后端分离方式:获取系统版本号 针对android和ios
     * @return ios 10.4.1
     */
    public static String getOsVersion(){
        return get().getOsVersion();
    }

    /**
     * 请求参数json
     * @return {"id":1,"age":12}
     */
    public static String getRequestBody(){
        return get().getRequestBody();
    }
}
