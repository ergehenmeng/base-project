package com.fanyin.model.ext;

/**
 * @author 二哥很猛
 * @date 2019/8/22 14:22
 */
public class RequestThreadLocal {

    private static final ThreadLocal<RequestMessage> TOKEN_LOCAL = ThreadLocal.withInitial(RequestMessage::new);

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
    public static String getJsonString(){
        return get().getJsonString();
    }
}
