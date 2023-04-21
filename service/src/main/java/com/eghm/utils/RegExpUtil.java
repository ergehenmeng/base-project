package com.eghm.utils;

import java.util.regex.Pattern;

/**
 * 正则表达式
 *
 * @author 二哥很猛
 * @date 2018/2/23 11:17
 */
public class RegExpUtil {

    private RegExpUtil() {
    }

    /**
     * 手机号正则表达式
     */
    private static final Pattern REGEXP_MOBILE = Pattern.compile("^((13\\d)|(14[579])|(15[^4])|(18\\d)|(17[0135678])|(19[026789]))\\d{8}$");

    /**
     * 隐藏字符串要替换的正则表达式值
     */
    static final String HIDDEN_REGEXP_VALUE = "$1****$2";

    /**
     * 判断字符串格式是否为手机号
     *
     * @param mobile 字符串
     * @return true:是手机号 false:不是
     */
    public static boolean mobile(String mobile) {
        return REGEXP_MOBILE.matcher(mobile).matches();
    }

}
