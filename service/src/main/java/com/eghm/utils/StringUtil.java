package com.eghm.utils;


import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 字符串日常工具类
 *
 * @author 二哥很猛
 * @since 2018/1/8 14:56
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

    /**
     * 随机字符串
     */
    private static final String NUMBER_LETTERS = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

    /**
     * 随机字符串小写
     */
    private static final String NUMBER_LOWER_LETTERS = "123456789abcdef";

    /**
     * cd_key生成
     */
    private static final String CD_KEY = "23456789ABCDEFGHGKLMNPQRSTUVWXYZ";

    /**
     * 进制串
     */
    private static final String ENCRYPT = "IFbH4SyMsPfeEw1CzQV6xtJK5ZUklOcDnYuNGArR9a0dphXiq8jg2mB7W3LTov";

    /**
     * 随机数字
     */
    private static final String NUMBER = "123456789";

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
     * 生成指定长度的随机字符串
     *
     * @param length 长度
     * @return 定长字符串 小写
     */
    public static String randomLowerCase(int length) {
        return random(NUMBER_LOWER_LETTERS, length);
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
        if (mobile == null) {
            return null;
        }
        return mobile.replaceAll(HIDDEN_REGEXP_MOBILE, RegExpUtil.HIDDEN_REGEXP_VALUE);
    }

    /**
     * 根据汉字获取首字母
     *
     * @param chinese 单个汉字 多个汉字默认取第一个
     * @return 首字母
     */
    public static String getInitial(String chinese) {
        if (StrUtil.isBlank(chinese)) {
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
            log.error("获取汉字首字母异常 chinese:[{}]", chinese, e);
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
        if (StrUtil.isBlank(chinese)) {
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
                log.warn("汉字解析拼音异常 chinese:[{}]", chinese, e);
            }
        }
        return builder.toString();
    }

    /**
     * 获取随机数字
     *
     * @param minValue 随机数最小
     * @param maxValue 随机数范围最大值 0~maxValue
     * @return minValue >= x < maxValue
     */
    public static int random(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(maxValue - minValue) + minValue;
    }

    /**
     * 数字进制 用于基础加密
     *
     * @param value value
     * @return 可解密
     */
    public static String encryptNumber(long value) {
        StringBuilder builder = new StringBuilder();
        int length = ENCRYPT.length();
        while (value > 0) {
            builder.append(ENCRYPT.charAt((int) (value % length)));
            value /= length;
        }
        return builder.toString();
    }

    /**
     * 数字进制 用于基础解密
     *
     * @param value value
     * @return 可解密
     */
    public static long decryptNumber(String value) {
        int scale = ENCRYPT.length();
        int length = value.length();
        long result = 0L;
        for (int i = length - 1; i >= 0; i--) {
            int index = ENCRYPT.indexOf(value.charAt(i));
            result = result * scale + index;
        }
        return result;
    }

    public static String generateCdKey() {
        return random(CD_KEY, 12) ;
    }


    public static void main(String[] args) {
        System.out.println(encryptNumber(10131417));
        int pid = 101320;
        StringBuilder builder = new StringBuilder();
        builder.append("\r\n");
        builder.append(sql(pid, "替换", 1013, 1, 15)).append("\r\n");

        int start = Integer.parseInt(pid + "10");
        int index = 1;
        for (int i = start; i <= start + 20; i++) {
            builder.append(sql(i, "替换", pid, 2, index * 10)).append("\r\n");
            index++;
        }
        log.info(builder.toString());
    }

    public static String sql(int id, String title, int pid, int grade, int sort) {
        String insert = "INSERT INTO `sys_menu` (`id`, `title`, `code`, `pid`, `grade`, `sort`) VALUES ('%s', '%s', '%s', '%s', '%d', '%d');";
        return String.format(insert, id, title, encryptNumber(id), pid, grade, sort);
    }


}
