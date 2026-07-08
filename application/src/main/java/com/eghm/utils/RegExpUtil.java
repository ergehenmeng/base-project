package com.eghm.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * 正则表达式
 *
 * @author 二哥很猛
 * @since 2018/2/23 11:17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegExpUtil {

    /**
     * 隐藏字符串要替换的正则表达式值
     */
    static final String HIDDEN_REGEXP_VALUE = "$1****$2";

    /**
     * 手机号正则表达式
     */
    private static final Pattern REGEXP_MOBILE = Pattern.compile("^((13\\d)|(14[579])|(15[^4])|(18\\d)|(17[0135678])|(19[026789]))\\d{8}$");

    /**
     * 密码格式, 必须包含:大小写字母,数字,特殊字符(@#&_)
     */
    private static final Pattern REGEXP_PASSWORD = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#&_]).{8,20}$");

    /**
     * 判断字符串格式是否为手机号
     *
     * @param mobile 字符串
     * @return true:是手机号 false:不是
     */
    public static boolean mobile(String mobile) {
        return REGEXP_MOBILE.matcher(mobile).matches();
    }

    /**
     * 判断密码是否符合要求
     * 8-16位,包含字母,数字,特殊字符
     *
     * @param password 字符串
     * @return true:符合密码要求
     */
    public static boolean password(String password) {
        return REGEXP_PASSWORD.matcher(password).matches();
    }
}
