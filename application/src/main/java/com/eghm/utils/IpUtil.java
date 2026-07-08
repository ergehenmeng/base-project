package com.eghm.utils;

import cn.hutool.core.net.NetUtil;
import com.eghm.constants.CommonConstant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * @author 二哥很猛
 * @since 2018/1/18 18:39
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
        String[] headers = new String[]{"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        String ip;
        for (String header : headers) {
            ip = request.getHeader(header);
            if (!NetUtil.isUnknown(ip)) {
                return NetUtil.getMultistageReverseProxyIp(ip);
            }
        }
        ip = request.getRemoteAddr();
        String proxyIp = NetUtil.getMultistageReverseProxyIp(ip);
        return "0:0:0:0:0:0:0:1".equals(proxyIp) ? "127.0.0.1" : proxyIp;
    }

}
