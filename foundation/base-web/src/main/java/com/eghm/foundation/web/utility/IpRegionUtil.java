package com.eghm.foundation.web.utility;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.service.Config;
import org.lionsoul.ip2region.service.Ip2Region;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
*  IP地域解析工具, 基于离线IP库(ip2region)解析IP地址所属省份-城市, ip2region_v4.xdb 数据文件需放置在 classpath 下
 *
 * @author wyb-eghm
 * @since 2026/9/3
 */
@Slf4j
public class IpRegionUtil {
    
    private static final String UNKNOWN = "unknown";
    
    private static final String LOCAL_REGION = "内网";
    
    private static final List<String> LOCAL_IPS = Arrays.asList("127.0.0.1", "0:0:0:0:0:0:0:1", "localhost");
    
    /**
     * 172.16.0.0 ~ 172.31.255.255 为B类私有地址段
     * 仅 172.16 ~ 172.31 是内网, 172.1 ~ 172.15 和 172.32 ~ 172.255 是公网
     */
    private static final Set<Integer> PRIVATE_172_PREFIXES = Set.of(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31);
    
    private static InputStream ip2regionIndex;
    
    static {
        try {
            ip2regionIndex = IpRegionUtil.class.getClassLoader().getResourceAsStream("ip2region_v4.xdb");
        } catch (Exception e) {
            log.warn("ip2region_v4.xdb 数据文件加载失败", e);
        }
    }
    
    /**
     * 根据IP地址解析所属地域(省份-城市)
     * 解析优先级: 本地回环 → 私有地址 → ip2region离线库
     *
     * @param ip IP地址
     * @return 地域描述, 如 "广东省-深圳市", 内网返回 "内网", 解析失败返回 "unknown"
     */
    public static String getRegion(String ip) {
        if (ip == null || LOCAL_IPS.contains(ip)) {
            return LOCAL_REGION;
        }
        if (isPrivateIp(ip)) {
            return LOCAL_REGION;
        }
        try {
            return doResolve(ip);
        } catch (Exception e) {
            log.warn("IP地域解析失败 [ip:{}]", ip, e);
            return UNKNOWN;
        }
    }
    
    /**
     * 判断是否为私有IP地址
     * A类: 10.0.0.0 ~ 10.255.255.255
     * B类: 172.16.0.0 ~ 172.31.255.255
     * C类: 192.168.0.0 ~ 192.168.255.255
     *
     * @param ip IP地址
     * @return true:私有地址 false:公网地址
     */
    private static boolean isPrivateIp(String ip) {
        if (ip.startsWith("10.") || ip.startsWith("192.168.")) {
            return true;
        }
        if (ip.startsWith("172.")) {
            String[] segments = ip.split("\\.");
            if (segments.length >= 2) {
                try {
                    int second = Integer.parseInt(segments[1]);
                    return PRIVATE_172_PREFIXES.contains(second);
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return false;
    }
    
    /**
     * 使用ip2region离线库解析IP地域
     * 返回格式: 中国|0|广东省|深圳市|电信 → 提取为 "广东省-深圳市"
     * ip2region.xdb 数据文件从 <a href="https://github.com/lionsoul2014/ip2region/releases">下载地址</a>
     *
     * @param ip 公网IP地址
     * @return 地域描述, 格式: "省份-城市"
     */
    private static String doResolve(String ip) {
        if (ip2regionIndex == null) {
            log.debug("ip2region_v4.xdb未加载,跳过IP地域解析 [ip:{}]", ip);
            return UNKNOWN;
        }
        try {
            Config v4Config = Config.custom().setCachePolicy(Config.BufferCache).setSearchers(15).setXdbInputStream(ip2regionIndex).asV4();
            Ip2Region ip2Region = Ip2Region.create(v4Config, null);
            String v4Region = ip2Region.search(ip);
            ip2Region.close();
            return parseRegion(v4Region);
        } catch (Exception e) {
            log.warn("ip2region解析异常 [ip:{}]", ip, e);
            return UNKNOWN;
        }
    }
    
    /**
     * 解析ip2region返回的原始地域字符串
     * 原始格式: "中国|0|广东省|深圳市|电信" 或 "中国|0|香港|0|0"
     * 提取规则: 取第3段(省份)和第4段(城市), 用"-"连接, 跳过"0"占位符
     *
     * @param rawRegion ip2region原始返回值
     * @return 格式化后的地域, 如 "广东省-深圳市" 或 "香港"
     */
    private static String parseRegion(String rawRegion) {
        if (rawRegion == null || rawRegion.isEmpty()) {
            return UNKNOWN;
        }
        String[] parts = rawRegion.split("\\|");
        String province = parts.length > 2 ? parts[2] : "";
        String city = parts.length > 3 ? parts[3] : "";
        
        if ("0".equals(province) && "0".equals(city)) {
            return UNKNOWN;
        }
        if ("0".equals(city) || city.equals(province)) {
            return province;
        }
        return province + "-" + city;
    }
}
