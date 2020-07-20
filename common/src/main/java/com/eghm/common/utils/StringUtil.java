package com.eghm.common.utils;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 字符串日常工具类
 *
 * @author 二哥很猛
 * @date 2018/1/8 14:56
 */
public class StringUtil extends StringUtils {

    /**
     * 随机字符串
     */
    private static final String NUMBER_LETTERS = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

    /**
     * 随机数字
     */
    private static final String NUMBER = "1234567890";

    /**
     * 默认随机字符串长度
     */
    private static final int DEFAULT_RANDOM_LENGTH = 4;

    /**
     * 手机号码隐藏
     */
    private static final String HIDDEN_REGEXP_MOBILE = "(\\d{3})\\d{4}(\\d{4})";

    /**
     * 汉字字符集
     */
    private static final String CHINESE_FONT = "[\\u4E00-\\u9FA5]+";

    /**
     * 英文字符集
     */
    private static final String ENGLISH_FONT = "[A-Za-z]+";

    private static final HanyuPinyinOutputFormat CHINA_FORMAT = new HanyuPinyinOutputFormat();

    static {
        CHINA_FORMAT.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        CHINA_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        CHINA_FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * 生成指定长度的随机字符串
     *
     * @param length 长度
     * @return 定长字符串
     */
    public static String random(int length) {
        return random(NUMBER_LETTERS, length);
    }

    /**
     * 生成指定长度的随机串,从指定字符串中生成
     *
     * @param scope  字符串选择范围
     * @param length 长度
     * @return 随机码
     */
    private static String random(String scope, int length) {
        if (length < 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        do {
            length--;
            builder.append(scope.charAt(ThreadLocalRandom.current().nextInt(scope.length())));
        } while (length > 0);
        return builder.toString();
    }

    /**
     * 生成6位随机字符串
     *
     * @return 字符串
     */
    public static String random() {
        return random(DEFAULT_RANDOM_LENGTH);
    }

    /**
     * 生成指定长度的数字(短信验证码)
     *
     * @param length 长度
     * @return 随机串
     */
    public static String randomNumber(int length) {
        return random(NUMBER, length);
    }

    /**
     * 生成定长的数字(短信验证码) 默认长度 DEFAULT_RANDOM_LENGTH
     *
     * @return 随机串
     */
    public static String randomNumber() {
        return randomNumber(DEFAULT_RANDOM_LENGTH);
    }

    /**
     * 隐藏手机号中间
     *
     * @param mobile 手机号码
     * @return 137****1234
     */
    public static String hiddenMobile(String mobile) {
        return mobile.replaceAll(HIDDEN_REGEXP_MOBILE, RegExpUtil.HIDDEN_REGEXP_VALUE);
    }

    /**
     * 根据汉字获取首字母
     *
     * @param chinese 单个汉字 多个汉字默认取第一个
     * @return 首字母
     */
    public static String getInitial(String chinese) {
        if (isBlank(chinese)) {
            return null;
        }
        char charAt = chinese.charAt(0);
        String firstChar = Character.toString(charAt);
        try {
            if (firstChar.matches(CHINESE_FONT)) {
                String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(charAt, CHINA_FORMAT);
                return Character.toString(stringArray[0].charAt(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return firstChar.toUpperCase();
    }


    /**
     * 汉字所有首字母
     *
     * @param chinese 中文字符
     * @return 二哥很猛 -> EGHM
     */
    public static String getCompleteInitial(String chinese) {
        if (isBlank(chinese)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chinese.length(); i++) {
            char charAt = chinese.charAt(i);
            try {
                String string = Character.toString(charAt);
                if (string.matches(CHINESE_FONT)) {
                    String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(charAt, CHINA_FORMAT);
                    builder.append(stringArray[0].charAt(0));
                } else if (string.matches(ENGLISH_FONT)) {
                    builder.append(string.toUpperCase());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }
}
