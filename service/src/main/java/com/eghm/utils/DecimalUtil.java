package com.eghm.utils;

import cn.hutool.core.math.Money;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
     * @return 金额，单位元
     * @since 5.7.11
     */
    public static String centToYuan(int cent) {
        long yuan = cent / 100;
        int centPart = cent % 100;
        DecimalFormat format = new DecimalFormat("#####0.00");
        return format.format(new Money(yuan, centPart).getAmount().doubleValue());
    }

    /**
     * 计算商品评价分数,保留一位小数
     *
     * @param totalScore 总分数
     * @param num        评价数
     * @return 分数
     */
    public static BigDecimal calcAvgScore(Integer totalScore, Integer num) {
        return BigDecimal.valueOf(totalScore).divide(BigDecimal.valueOf(num), 1, RoundingMode.HALF_UP);
    }

}
