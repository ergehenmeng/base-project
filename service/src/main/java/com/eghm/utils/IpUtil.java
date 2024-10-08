package com.eghm.utils;

import cn.hutool.extra.servlet.ServletUtil;
import com.eghm.constants.CommonConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;


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
        String ip = ServletUtil.getClientIP(request);
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
