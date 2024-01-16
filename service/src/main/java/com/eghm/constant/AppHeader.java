package com.eghm.constant;

/**
 * 移动端请求头信息
 *
 * @author 二哥很猛
 * @date 2018/8/14 16:57
 */
public class AppHeader {

    /**
     * 基础参数 请求渠道
     */
    public static final String CHANNEL = "Channel";
    /**
     * 基础参数 软件版本号
     */
    public static final String VERSION = "Version";
    /**
     * 基础参数 操作系统版本
     */
    public static final String OS_VERSION = "Os-Version";
    /**
     * 基础参数   设备厂商
     */
    public static final String DEVICE_BRAND = "Device-Brand";
    /**
     * 基础参数 设备型号
     */
    public static final String DEVICE_MODEL = "Device-Model";
    /**
     * 设备唯一序列号
     */
    public static final String SERIAL_NUMBER = "Serial-Number";
    /**
     * 用户登陆信息 accessToken
     */
    public static final String TOKEN = "Token";
    /**
     * 用户登陆信息 refreshToken
     */
    public static final String REFRESH_TOKEN = "Refresh-Token";
    /**
     * 签名 (第三方使用)
     */
    public static final String SIGNATURE = "signature";
    /**
     * 签名Key (第三方使用)
     */
    public static final String APP_KEY = "appKey";
    /**
     * 时间戳 (第三方使用)
     */
    public static final String TIMESTAMP = "timestamp";

    private AppHeader() {
    }

}
