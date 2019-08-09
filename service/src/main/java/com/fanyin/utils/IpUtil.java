package com.fanyin.utils;

import com.fanyin.common.constant.CommonConstant;

import javax.servlet.http.HttpServletRequest;


/**
 * @author 二哥很猛
 * @date 2018/1/18 18:39
 */
public class IpUtil {


    /**
     * 获取ip地址
     * @param request 请求servlet
     * @return ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            return CommonConstant.UNKNOWN;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
