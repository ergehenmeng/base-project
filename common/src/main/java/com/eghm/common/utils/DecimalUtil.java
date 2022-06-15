package com.eghm.common.utils;

import cn.hutool.core.math.Money;

import java.text.DecimalFormat;

/**
 * @author wyb
 * @date 2022/6/12 19:03
 */
public class DecimalUtil {

    private DecimalUtil() {}

    /**
     * 金额元转换为分
     *
     * @param yuan 金额，单位元
     * @return 金额，单位分
     * @since 5.7.11
     */
    public static int yuanToCent(double yuan) {
        return (int)new Money(yuan).getCent();
    }

    /**
     * 金额分转换为元
     *
     * @param cent 金额，单位分
     * @return 金额，单位元
     * @since 5.7.11
     */
    public static String centToYuan(int cent) {
        long yuan = cent / 100;
        int centPart = cent % 100;
        DecimalFormat format = new DecimalFormat("#####0.00");
        return format.format(new Money(yuan, centPart).getAmount().doubleValue());
    }

}
