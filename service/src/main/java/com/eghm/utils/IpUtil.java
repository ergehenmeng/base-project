package com.eghm.utils;

import com.eghm.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;


/**
 * @author 二哥很猛
 * @date 2018/1/18 18:39
 */
@Slf4j
public class IpUtil {


    /**
     * 获取ip地址
     *
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
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取本机ip地址
     *
     * @return 127.0.0.1
     */
    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.error("获取本机ip失败", e);
        }
        return null;
    }

    /**
     * 将ip转为long类型 ip4类型的
     * @param ip  ip地址
     * @return long
     */
    public static long longIpv4(String ip){
        String[] split = ip.split("\\.");
        return (Long.parseLong(split[0]) << 24) + (Long.parseLong(split[1]) << 16) + (Long.parseLong(split[2]) << 8)  + Long.parseLong(split[3]);
    }

}
