package com.eghm.common.utils;


import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 字符串日常工具类
 *
 * @author 二哥很猛
 * @date 2018/1/8 14:56
 */
@Slf4j
public class StringUtil {

    private StringUtil() {
    }

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
     * 获取身份证年龄
     *
     * @param idCard 身份证编号
     * @return 不合法, 异常均返回0
     */
    public static int getAge(String idCard) {
        if (!IdcardUtil.isValidCard18(idCard)) {
            return 0;
        }
        String birth = IdcardUtil.getBirthByIdCard(idCard);

        int birthYear = Integer.parseInt(birth.substring(0, 4));
        int birthMonth = Integer.parseInt(birth.substring(4, 6));
        int birthDay = Integer.parseInt(birth.substring(6, 8));

        LocalDate now = LocalDate.now();
        int nowYear = now.getYear();
        int nowMonth = now.getDayOfMonth();
        int nowDay = now.getDayOfMonth();

        int age = 0;
        if (nowYear <= birthYear) {
            return age;
        }
        age = nowYear - birthYear;
        //生日月份大于当前月份表明今年还没过生日,或者生日月份等于当前月份,生日天比当天大已经表示今天未过生日
        boolean flag = birthMonth > nowMonth || (birthMonth == nowMonth && birthDay > nowDay);
        if (flag) {
            return age - 1;
        }
        return age;
    }

    /**
     * 获取随机数字
     * @param minValue 随机数最小
     * @param maxValue 随机数范围最大值 0~maxValue
     * @return minValue >= x < maxValue
     */
    public static int random(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(maxValue - minValue) + minValue;
    }
}
