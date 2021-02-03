package com.eghm.common.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 版本比较工具类
 *
 * @author 二哥很猛
 * @date 2018/3/14 9:04
 */
@Slf4j
public class VersionUtil {

    private VersionUtil() {
    }

    private static final String[] REPLACE_CHAR = {"v", "version"};

    /**
     * version是否大于等于指定的target版本
     *
     * @param version 前台传递过来的版本
     * @param target  后台版本
     * @return 如果version大于则true 否则false
     */
    public static boolean gte(String version, String target) {
        String s = replace(version);
        String t = replace(target);

        //版本刚好等于或者主版本等于
        if (s.startsWith(t)) {
            return true;
        }

        String[] versions = s.split("\\.");
        String[] targets = t.split("\\.");
        if (versions.length >= targets.length) {
            return compare(versions, targets);
        }
        return compare(targets, versions);
    }

    /**
     * 比较大小 第一个是否大于第二个
     *
     * @param source 第一个
     * @param target 第二个
     * @return true  source大于target false source小于target
     */
    private static boolean compare(String[] source, String[] target) {
        for (int i = 0; i < target.length; i++) {
            String v = source[i];
            String location = target[i];
            if (Integer.parseInt(v) > Integer.parseInt(location)) {
                return true;
            }
        }

        return false;
    }

    private static String replace(String str) {
        for (String replace : REPLACE_CHAR) {
            str = str.replace(replace, "");
        }
        return str;
    }

    /**
     * 将版本号转换为数字,以2位的版本号补零计算得到
     * @param version  v2.10.2
     * @return 21002
     */
    public static int parseInt(String version) {
        String[] split = replace(version).split("\\.");
        StringBuilder builder = new StringBuilder();
        try {
            for (String v : split) {
                builder.append(String.format("%02d", Integer.parseInt(v)));
            }
            return Integer.parseInt(builder.toString());
        } catch (NumberFormatException e) {
            log.error("版本号格式化异常 [{}]", version, e);
            throw new RuntimeException("版本号解析失败");
        }
    }

}
