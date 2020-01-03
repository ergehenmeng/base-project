package com.fanyin.common.utils;

import java.util.regex.Pattern;

/**
 * 正则表达式
 *
 * @author 二哥很猛
 * @date 2018/2/23 11:17
 */
public class RegExpUtil {

    /**
     * 身份证校验正则表达式
     */
    private static final String REGEXP_ID_CARD = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";

    /**
     * 手机号正则表达式
     */
    private static final String REGEXP_MOBILE = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8])|(19[0,2,6,7,8,9]))\\\\d{8}$";

    /**
     * 隐藏字符串要替换的正则表达式值
     */
    static final String HIDDEN_REGEXP_VALUE = "$1****$2";

    /**
     * 校验身份证是否合法 只做较为基础的校验
     *
     * @param idCard 身份证编号 15位或18位
     * @return boolean true:合法 false:不合法
     */
    public static boolean idCard(String idCard) {
        return match(REGEXP_ID_CARD, idCard);
    }

    /**
     * 根据给定的正则表达式判断字符串是否匹配
     *
     * @param regExp 正则表达式
     * @param str    字符串
     * @return true匹配成功, false匹配不成功
     */
    private static boolean match(String regExp, String str) {
        return Pattern.matches(regExp, str);
    }

    /**
     * 判断字符串格式是否为手机号
     *
     * @param mobile 字符串
     * @return true:是手机号 false:不是
     */
    public static boolean mobile(String mobile) {
        return match(REGEXP_MOBILE, mobile);
    }
}
