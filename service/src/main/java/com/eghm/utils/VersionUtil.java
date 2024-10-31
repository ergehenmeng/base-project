package com.eghm.utils;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 版本比较工具类
 *
 * @author 二哥很猛
 * @since 2018/3/14 9:04
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VersionUtil {

    private static final String[] REPLACE_CHAR = {"v"};

    /**
     * version是否大于等于指定的target版本
     * 版本号格式必须遵循以下格式,子版本最多两位
     * 合法: v2.4.3, v2.42.23, 2.43.3
     * 非法: 2.432.323
     *
     * @param source 原版本号
     * @param target 目标版本号
     * @return 如果source大于等于target则true   否则false
     */
    public static boolean gte(String source, String target) {
        return parseInt(source) >= parseInt(target);
    }

    private static String replace(String str) {
        for (String replace : REPLACE_CHAR) {
            str = str.replace(replace, "");
        }
        return str;
    }

    /**
     * 将版本号转换为数字,以2位的版本号补零计算得到
     *
     * @param version v2.10.2
     * @return 21002
     */
    public static int parseInt(String version) {
        String[] split = replace(version).split("\\.");
        StringBuilder builder = new StringBuilder();
        for (String v : split) {
            if (v.length() > 2) {
                log.info("版本号解析错误 [{}]", version);
                throw new BusinessException(ErrorCode.VERSION_ERROR);
            }
            builder.append(String.format("%02d", Integer.parseInt(v)));
        }
        return Integer.parseInt(builder.toString());
    }

}
