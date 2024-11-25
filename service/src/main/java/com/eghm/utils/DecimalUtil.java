package com.eghm.utils;

import cn.hutool.core.math.Money;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author 二哥很猛
 * @since 2022/6/12 19:03
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DecimalUtil {

    /**
     * 金额元转换为分
     *
     * @param yuan 金额，单位元
     * @return 金额，单位分
     * @since 5.7.11
     */
    public static int yuanToCent(double yuan) {
        return (int) new Money(yuan).getCent();
    }

    /**
     * 金额元转换为分
     *
     * @param yuan 金额，单位元
     * @return 金额，单位分
     * @since 5.7.11
     */
    public static int yuanToCent(String yuan) {
        return (int) new Money(yuan).getCent();
    }

    /**
     * 金额分转换为元
     *
     * @param cent 金额，单位分
     * @return 金额，单位元 保留两位小数
     * @since 5.7.11
     */
    public static String centToYuan(int cent) {
        long yuan = cent / 100;
        int centPart = cent % 100;
        DecimalFormat format = new DecimalFormat("#####0.00");
        return format.format(new Money(yuan, centPart).getAmount().doubleValue());
    }

    /**
     * 金额分转换为元
     *
     * @param cent 金额，单位分
     * @return 金额，单位元 不保留两位小数(有多少保留多少)
     * @since 5.7.11
     */
    public static String centToYuanOmit(int cent) {
        return new BigDecimal(centToYuan(cent)).stripTrailingZeros().toPlainString();
    }

}
