package com.fanyin.common.utils;


import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.ParameterException;

import java.time.LocalDate;

/**
 * 银行卡信息
 *
 * @author 二哥很猛
 * @date 2018/1/17 11:11
 */
public class BankCardUtil {


    /**
     * 身份证号码长度 默认18位
     */
    private static final int ID_CARD_LENGTH = 18;

    /**
     * 身份证出生年日短格式
     */
    private static final int BIRTH_DAY_SHORT = 6;


    /**
     * 18位身份证隐藏需要的正则表达式
     */
    private static final String HIDDEN_REGEXP_LONG = "(\\d{6})\\d{8}(\\d{3}[0-9Xx])";
    /**
     * 15位身份证隐藏需要的正则表达式
     */
    private static final String HIDDEN_REGEXP_SHORT = "(\\d{6})\\d{6}(\\d{3})";

    /**
     * 对身份证DES加密默认的秘钥
     */
    private static final String DEFAULT_DES_PASSWORD = "8e5f58369fadfddbed40532d4f1552ac";


    /**
     * 根据身份证获取用户的出生年月日
     *
     * @param idCard 身份证号码
     * @return 出生年月日 yyyyMMdd或yyMMdd
     */
    public static String getBirthDay(String idCard) {
        if (!RegExpUtil.idCard(idCard)) {
            throw new ParameterException(ErrorCode.ID_CARD_ERROR);
        }
        if (idCard.length() == ID_CARD_LENGTH) {
            return idCard.substring(6, 14);
        } else {
            return idCard.substring(6, 12);
        }
    }

    /**
     * 根据身份证获取用户的出生年月日 标准格式yyyyMMdd
     *
     * @param idCard 身份证号码
     * @return 出生年月日 yyyyMMdd
     */
    public static String getNormalBirthDay(String idCard) {
        String birthDay = getBirthDay(idCard);
        return birthDay.length() == BIRTH_DAY_SHORT ? "19" + birthDay : birthDay;
    }

    /**
     * 获取身份证年龄
     *
     * @param idCard 身份证编号
     * @return 不合法, 异常均返回0
     */
    public static int getAge(String idCard) {
        if (!RegExpUtil.idCard(idCard)) {
            return 0;
        }
        String birth = getNormalBirthDay(idCard);

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
     * 隐藏出生年月日
     *
     * @param idCard 身份证号码
     * @return 隐藏出生年月日后的身份证 例如:310223****6831
     */
    public static String hiddenIdCard(String idCard) {
        if (idCard.length() == ID_CARD_LENGTH) {
            return idCard.replaceAll(HIDDEN_REGEXP_LONG, RegExpUtil.HIDDEN_REGEXP_VALUE);
        }
        return idCard.replaceAll(HIDDEN_REGEXP_SHORT, RegExpUtil.HIDDEN_REGEXP_VALUE);
    }

    /**
     * 加密身份证出生年月日
     *
     * @param idCard 身份证号码
     * @return 加密后的出生年月日
     */
    public static String encryptBirthDay(String idCard) {
        String birthDay = getBirthDay(idCard);
        return DesUtil.encrypt3Des(birthDay, DEFAULT_DES_PASSWORD);
    }

    /**
     * 解密身份证
     *
     * @param idCard 身份证 出生年月日已加密:310223****6831
     * @param data   出生年月日加密串
     * @return 真实身份证号码 例如:310223198901146831
     */
    public static String decryptIdCard(String idCard, String data) {
        String s = DesUtil.decrypt3Des(data, DEFAULT_DES_PASSWORD);
        return idCard.replace("****", s);
    }

}
